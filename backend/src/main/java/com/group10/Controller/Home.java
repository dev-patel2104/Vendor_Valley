package com.group10.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Home {
    
    @RequestMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("This is spring boot home/landing page!");
    }
}
