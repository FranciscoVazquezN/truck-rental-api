package com.truckrental.service;

import com.truckrental.entity.Camion;
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

    public List<Camion> buscar(Double capacidad) {
        StringBuilder sql = new StringBuilder("select c from Camion c where activo = true");
        if (capacidad != null) {
            sql.append(" and c.capacidad >= :capacidad");
        }
        TypedQuery<Camion> query = em.createQuery(sql.toString(), Camion.class);
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
        camion.setCapacidad(datos.getCapacidad());
        return camion;
    }

    @Transactional
    public void sofDelete(Integer id) {
        Camion camion = em.find(Camion.class, id);
        if (camion != null) {
            camion.setActivo(false);
            em.merge(camion);
        }
    }

}
