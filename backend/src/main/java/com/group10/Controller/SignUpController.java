package com.group10.Controller;

import com.group10.Model.SignUpModel;
import com.group10.Response.SuccessResponse;
import com.group10.Service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {


    @Autowired
    SignUpService signUpService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> signUpVendor(@RequestBody SignUpModel signUpModel) {
        return new ResponseEntity<>(signUpService.signUpVendor(signUpModel), HttpStatus.OK);
    }
}
