package com.vendorvalley_api.vendorvalley_api.controller;

import com.vendorvalley_api.vendorvalley_api.model.SignUpModel;
import com.vendorvalley_api.vendorvalley_api.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {


    @Autowired
    SignUpService signUpService;

    @PostMapping("/signup")
    public void signUpVendor(@RequestBody SignUpModel signUpModel) {
        signUpService.signUpVendor(signUpModel);
    }
}
