package com.example.basicrestapi3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Transactional
public class ActiveFactDao {

    @PersistenceContext
    private EntityManager em;

    // Saves a fact as the currently posted fact - clears any previous one first
    public void save(ActiveFact activeFact) {
        clearTable();
        em.persist(activeFact);
    }
    // Deletes the active fact - removes what was posted
    public void clearTable() {

        em.createQuery("DELETE FROM ActiveFact").executeUpdate();
    }

    // Gets the most recent posted fact - returns null if none exists
    public ActiveFact getCurrent() {
        List<ActiveFact> results = em.createQuery(
                        "SELECT a FROM ActiveFact a ORDER BY a.postedAt DESC",
                        ActiveFact.class)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}