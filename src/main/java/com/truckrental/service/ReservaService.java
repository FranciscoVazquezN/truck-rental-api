package com.truckrental.service;

import com.truckrental.entity.Camion;
import com.truckrental.entity.Reserva;
import com.truckrental.entity.ReservaCamion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ReservaService {
    @Inject
    EntityManager em;

    @Transactional
    public Reserva save(Reserva reserva) {
        asignarCamiones(reserva);
        em.persist(reserva);
        return reserva;
    }

    public void asignarCamiones(Reserva reserva) {
        List<Camion> disponibles = buscarDisponibles(reserva.getFechaDesde(), reserva.getFechaHasta());
        List<ReservaCamion> lista = new ArrayList<>();

        double volumenRestante = reserva.getVolumenTotal();

        for (Camion c : disponibles) {
            if (volumenRestante <= 0) break;

            ReservaCamion rc = new ReservaCamion();
            rc.setReserva(reserva);
            rc.setCamion(c);
            double volumenAsignado = Math.min(c.getCapacidad(), volumenRestante);
            rc.setVolumenAsignado(volumenAsignado);
            volumenRestante -= volumenAsignado;
            lista.add(rc);
        }

        if (volumenRestante > 0) {
            throw new RuntimeException("No hay capacidad suficiente");
        }
        reserva.setCamiones(lista);
    }

    public List<Reserva> buscar(String origen, String destino) {
        StringBuilder sql = new StringBuilder("select c from Reserva c where 1=1");
        if (origen != null) {
            sql.append(" and lower(c.origen) like lower(:origen)");
        }
        if (destino != null) {
            sql.append(" and lower(c.destino) like lower(:destino)");
        }

        TypedQuery<Reserva> query = em.createQuery(sql.toString(), Reserva.class);
        if (origen != null) {
            query.setParameter("origen", "%" + origen + "%");
        }
        if (destino != null) {
            query.setParameter("destino", "%" + destino + "%");
        }
        return query.getResultList();
    }

    @Transactional
    public Reserva cancelar(Integer id) {
        Reserva Reserva = em.find(Reserva.class, id);
        if (Reserva == null) {
            throw new NotFoundException("Reserva con id " + id + " no encontrado");
        }
        Reserva.setCancelado(true);
        return Reserva;
    }

    public List<Camion> buscarDisponibles(LocalDate desde, LocalDate hasta) {
        String sql = """
                SELECT c
                FROM Camion c
                WHERE c.activo = true
                AND NOT EXISTS (
                    SELECT rc
                    FROM ReservaCamion rc
                    WHERE rc.camion = c
                    AND (
                        :desde <= rc.reserva.fechaHasta
                        AND :hasta >= rc.reserva.fechaDesde
                    )
                )
                ORDER BY c.capacidad DESC
                """;
        TypedQuery<Camion> query = em.createQuery(sql, Camion.class);
        query.setParameter("desde", desde);
        query.setParameter("hasta", hasta);
        return query.getResultList();
    }
}
