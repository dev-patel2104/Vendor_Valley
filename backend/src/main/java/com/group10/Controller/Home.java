package com.group10.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Home {

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("This is spring boot home/landing page!");
    }


}
