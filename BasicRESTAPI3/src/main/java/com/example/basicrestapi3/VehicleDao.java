package com.example.basicrestapi3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class VehicleDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Vehicle vehicle) {
        em.persist(vehicle);
    }

    public Vehicle getById(int id) {
        return em.find(Vehicle.class, id);
    }

    public void update(Vehicle vehicle) {
        em.merge(vehicle);
    }

    public void delete(Vehicle vehicle) {
        em.remove(vehicle);
    }

    public List<Vehicle> getLatest() {
        return em.createQuery("SELECT v FROM Vehicle v ORDER BY v.id DESC", Vehicle.class)
                .setMaxResults(10)
                .getResultList();
    }

}