package com.group10.VendorValley;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @RequestMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("This is spring boot home/landing page!");
    }


}
