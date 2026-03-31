package com.truckrental.service;

import com.truckrental.model.Camion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;


@ApplicationScoped
public class CamionService {
    @Inject
    EntityManager em;

    @Transactional
    public Camion save(Camion camion) {
        em.persist(camion);
        return camion;
    }

    public List<Camion> buscar(String marca, String modelo, Float capacidad) {
        StringBuilder sql = new StringBuilder("select c from Camion c where 1=1");
        if (marca != null) {
            sql.append(" and lower(c.marca) like lower(:marca)");
        }
        if (modelo != null) {
            sql.append(" and lower(c.modelo) like lower(:modelo)");
        }
        if (capacidad != null) {
            sql.append(" and c.capacidad >= :capacidad");
        }
        TypedQuery<Camion> query = em.createQuery(sql.toString(), Camion.class);
        if (marca != null) {
            query.setParameter("marca", "%"  + marca + "%");
        }
        if (modelo != null) {
            query.setParameter("modelo", "%"  + modelo + "%");
        }
        if (capacidad != null) {
            query.setParameter("capacidad", capacidad);
        }
        return query.getResultList();
    }

    @Transactional
    public Camion update(Integer id, Camion datos) {
        Camion camion = em.find(Camion.class, id);
        if (camion == null) {
            throw new NotFoundException("Camion con id " + id + " no encontrado");
        }
        camion.setMarca(datos.getMarca());
        camion.setModelo(datos.getModelo());
        camion.setCapacidad(datos.getCapacidad());
        return camion;
    }
}
