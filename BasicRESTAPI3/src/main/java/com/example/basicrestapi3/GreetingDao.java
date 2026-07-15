package com.example.basicrestapi3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class GreetingDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Greeting greeting) {
        em.persist(greeting);
    }

    public Greeting getById(Integer id) {
        return em.find(Greeting.class, id);
    }

    public List<Greeting> getAll() {
        return em.createQuery("SELECT g FROM Greeting g", Greeting.class).getResultList();
    }

    public void update(Greeting greeting) {
        em.merge(greeting);
    }

    public void delete(Greeting greeting) {
        em.remove(greeting);
    }

}