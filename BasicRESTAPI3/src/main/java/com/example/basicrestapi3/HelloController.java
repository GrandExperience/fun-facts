package com.example.basicrestapi3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private GreetingDao greetingDao;

    @PostMapping("/createGreeting")
    public Greeting createGreeting(@RequestBody String greeting) {
        Greeting g = new Greeting(greeting);
        greetingDao.create(g);
        return g;
    }

    @GetMapping("/getGreeting/{id}")
    public Greeting getGreeting(@PathVariable Integer id) {
        return greetingDao.getById(id);
    }

}