package com.group10.Service;

import com.group10.Model.SignUpModel;
import com.group10.Repository.SignUpRepository;
import com.group10.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    @Autowired
    SignUpRepository signUpRepository;


    public SuccessResponse signUpVendor(SignUpModel signUpModel) {
        boolean isVendorAdded = signUpRepository.saveVendor(signUpModel);
        if (isVendorAdded) {
            return new SuccessResponse("Vendor registered Successfully !!");
        }
        return new SuccessResponse(""); //todo: for failure case?
    }
}
