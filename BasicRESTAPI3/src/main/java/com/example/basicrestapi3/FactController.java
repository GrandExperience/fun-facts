package com.example.basicrestapi3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class FactController {

    @Autowired
    private FactDao factDao;

    @Autowired
    private ActiveFactDao activeFactDao;

    @RequestMapping(value = "/addFact", method = RequestMethod.POST)
    public ResponseEntity<Fact> addFact(@RequestBody Map<String, Object> body) {
        String factText = stringValue(body.get("factText"));
        if (factText == null || factText.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Fact fact = new Fact(factText.trim(), stringValue(body.get("source")));
        if (body.containsKey("active")) {
            fact.setActive(booleanValue(body.get("active")));
        }

        factDao.create(fact);
        return ResponseEntity.ok(fact);
    }

    @RequestMapping(value = "/updateFact", method = RequestMethod.PUT)
    public ResponseEntity<Fact> updateFact(@RequestBody Map<String, Object> body) {
        if (!body.containsKey("id")) {
            return ResponseEntity.badRequest().build();
        }

        int id = Integer.parseInt(body.get("id").toString());
        Fact fact = factDao.getById(id);
        if (fact == null) {
            return ResponseEntity.notFound().build();
        }

        if (body.containsKey("factText")) {
            String factText = stringValue(body.get("factText"));
            if (factText == null || factText.isBlank()) {
                return ResponseEntity.badRequest().build();
            }
            fact.setFactText(factText.trim());
        }
        if (body.containsKey("source")) {
            fact.setSource(stringValue(body.get("source")));
        }
        if (body.containsKey("active")) {
            fact.setActive(booleanValue(body.get("active")));
        }

        factDao.update(fact);
        return ResponseEntity.ok(fact);
    }

    @RequestMapping(value = "/getFact/{id}", method = RequestMethod.GET)
    public ResponseEntity<Fact> getFact(@PathVariable("id") int id) {
        Fact fact = factDao.getById(id);
        if (fact == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fact);
    }

    @RequestMapping(value = "/getDailyFact", method = RequestMethod.GET)
    public ResponseEntity<Fact> getDailyFact() {
        return factDao.getRandomActive()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/getActiveFact", method = RequestMethod.GET)
    public ResponseEntity<ActiveFact> getActiveFact() {
        return activeFactDao.getCurrent()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/getAllFacts", method = RequestMethod.GET)
    public List<Fact> getAllFacts() {
        return factDao.getAll();
    }

    @RequestMapping(value = "/deleteFact/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFact(@PathVariable("id") int id) {
        Fact fact = factDao.getById(id);
        if (fact == null) {
            return ResponseEntity.notFound().build();
        }
        factDao.delete(fact);
        return ResponseEntity.ok("Fact deleted.");
    }

    @RequestMapping(value = "/resetFacts", method = RequestMethod.POST)
    public ResponseEntity<String> resetAllFacts() {
        factDao.resetAllFacts();
        return ResponseEntity.ok("All facts have been reset to active.");
    }

    @RequestMapping(value = "/countActive", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Long>> countActiveFacts() {
        long count = factDao.countActiveFacts();
        return ResponseEntity.ok(Map.of("activeFacts", count));
    }

    private String stringValue(Object value) {
        return value == null ? null : value.toString();
    }

    private boolean booleanValue(Object value) {
        if (value instanceof Boolean bool) {
            return bool;
        }
        return Boolean.parseBoolean(value.toString());
    }
}