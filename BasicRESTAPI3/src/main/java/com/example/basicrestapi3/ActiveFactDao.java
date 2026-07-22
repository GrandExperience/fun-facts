package com.example.basicrestapi3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@Transactional
public class ActiveFactDao {

    @PersistenceContext
    private EntityManager em;

    public void save(ActiveFact activeFact) {
        clearTable();
        em.persist(activeFact);
    }

    public void clearTable() {
        em.createQuery("DELETE FROM ActiveFact").executeUpdate();
    }

    public Optional<ActiveFact> getCurrent() {
        try {
            ActiveFact active = em.createQuery(
                            "SELECT a FROM ActiveFact a ORDER BY a.postedAt DESC",
                            ActiveFact.class)
                    .setMaxResults(1)
                    .getSingleResult();
            return Optional.of(active);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean hasActiveFact() {
        Long count = em.createQuery("SELECT COUNT(a) FROM ActiveFact a", Long.class)
                .getSingleResult();
        return count > 0;
    }
}