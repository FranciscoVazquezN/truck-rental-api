package com.truckrental.service;

import com.truckrental.model.Reserva;
import com.truckrental.model.Reserva;
import com.truckrental.model.Reserva;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ReservaService {
    @Inject
    EntityManager em;

    @Transactional
    public Reserva save(Reserva reserva) {
        em.persist(reserva);
        return reserva;
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
            query.setParameter("origen", "%"  + origen + "%");
        }
        if (destino != null) {
            query.setParameter("destino", "%"  + destino + "%");
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
}
