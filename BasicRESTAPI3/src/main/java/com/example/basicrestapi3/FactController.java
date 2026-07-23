package com.example.basicrestapi3;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Fact addFact(@RequestBody Map<String, Object> body) {
        String factText = body.get("factText").toString();
        String source = body.containsKey("source") ? body.get("source").toString() : null;

        Fact fact = new Fact(factText, source);
        if (body.containsKey("active")) {
            fact.setActive(Boolean.parseBoolean(body.get("active").toString()));
        }
        factDao.create(fact);
        return fact;
    }

    @RequestMapping(value = "/updateFact", method = RequestMethod.PUT)
    public Fact updateFact(@RequestBody Map<String, Object> body) {
        int id = Integer.parseInt(body.get("id").toString());
        Fact fact = factDao.getById(id);

        if (body.containsKey("factText")) {
            fact.setFactText(body.get("factText").toString());
        }
        if (body.containsKey("source")) {
            fact.setSource(body.get("source").toString());
        }
        if (body.containsKey("active")) {
            fact.setActive(Boolean.parseBoolean(body.get("active").toString()));
        }

        factDao.update(fact);
        return fact;
    }

    @RequestMapping(value = "/getFact/{id}", method = RequestMethod.GET)
    public Fact getFact(@PathVariable int id) {
        return factDao.getById(id);
    }

    @RequestMapping(value = "/getDailyFact", method = RequestMethod.GET)
    public Fact getDailyFact() {
        return factDao.getRandomActive();
    }

    @RequestMapping(value = "/getActiveFact", method = RequestMethod.GET)
    public ActiveFact getActiveFact() {
        return activeFactDao.getCurrent();
    }

    @RequestMapping(value = "/getAllFacts", method = RequestMethod.GET)
    public List<Fact> getAllFacts() {
        return factDao.getAll();
    }

    @RequestMapping(value = "/deleteFact/{id}", method = RequestMethod.DELETE)
    public String deleteFact(@PathVariable int id) {
        Fact fact = factDao.getById(id);
        factDao.delete(fact);
        return "Fact deleted.";
    }

    @RequestMapping(value = "/resetFacts", method = RequestMethod.POST)
    public String resetAllFacts() {
        factDao.resetAllFacts();
        return "All facts have been reset to active.";
    }

    @RequestMapping(value = "/countActive", method = RequestMethod.GET)
    public int countActiveFacts() {
        return factDao.countActiveFacts();
    }
    @RequestMapping(value = "/getLatestFacts", method = RequestMethod.GET)
    public List<Fact> getLatestFacts() {
        return factDao.getLatestFacts();
    }
}