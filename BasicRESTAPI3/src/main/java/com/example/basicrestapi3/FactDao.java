package com.example.basicrestapi3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Transactional
public class FactDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Fact fact) {
        em.persist(fact);
    }

    public Fact getById(int id) {
        return em.find(Fact.class, id);
    }

    public List<Fact> getAll() {
        return em.createQuery("SELECT f FROM Fact f ORDER BY f.id", Fact.class)
                .getResultList();
    }

    public Fact getRandomActive() {
        List<Fact> results = em.createNativeQuery(
                        "SELECT * FROM fun_facts WHERE is_active = true ORDER BY RANDOM() LIMIT 1",
                        Fact.class)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public void update(Fact fact) {
        em.merge(fact);
    }

    public void deactivate(Fact fact) {
        Fact managed = em.contains(fact) ? fact : em.merge(fact);
        managed.setActive(false);
        em.merge(managed);

        if (countActiveFacts() == 0) {
            resetAllFacts();
        }
    }

    public void delete(Fact fact) {
        em.remove(em.contains(fact) ? fact : em.merge(fact));
    }

    public void resetAllFacts() {
        em.createQuery("UPDATE Fact f SET f.active = true").executeUpdate();
    }

    public int countActiveFacts() {
        Long count = em.createQuery("SELECT COUNT(f) FROM Fact f WHERE f.active = true", Long.class)
                .getSingleResult();
        return count.intValue();

    }
    public List<Fact> getLatestFacts() {
        return em.createQuery("SELECT f FROM Fact f ORDER BY f.createdAt DESC", Fact.class)
                .setMaxResults(5)
                .getResultList();
    }
}