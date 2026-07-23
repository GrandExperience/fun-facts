package com.example.basicrestapi3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActiveFactController {

    @Autowired
    private ActiveFactDao activeFactDao;

    @RequestMapping(value = "/getCurrentActive", method = RequestMethod.GET)
    public ActiveFact getCurrentActiveFact() {
        return activeFactDao.getCurrent();
    }

    @RequestMapping(value = "/clearActive", method = RequestMethod.DELETE)
    public String clearActiveFact() {
        activeFactDao.clearTable();
        return "Active fact cleared.";
    }
}