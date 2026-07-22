package com.example.basicrestapi3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/active")
public class ActiveFactController {

    @Autowired
    private ActiveFactDao activeFactDao;

    @GetMapping("/current")
    public ResponseEntity<ActiveFact> getCurrentActiveFact() {
        return activeFactDao.getCurrent()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearActiveFact() {
        if (!activeFactDao.hasActiveFact()) {
            return ResponseEntity.notFound().build();
        }
        activeFactDao.clearTable();
        return ResponseEntity.ok("Active fact cleared.");
    }
}