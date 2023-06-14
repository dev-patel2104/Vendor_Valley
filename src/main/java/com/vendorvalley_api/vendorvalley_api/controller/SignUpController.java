package com.vendorvalley_api.vendorvalley_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {


    @PostMapping("/signup")
    public void signUpVendor() {
        return;
    }
}
