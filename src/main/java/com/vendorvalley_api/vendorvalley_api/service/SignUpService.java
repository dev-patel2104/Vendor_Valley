package com.vendorvalley_api.vendorvalley_api.service;

import com.vendorvalley_api.vendorvalley_api.model.SignUpModel;
import com.vendorvalley_api.vendorvalley_api.repository.SignUpRepository;
import com.vendorvalley_api.vendorvalley_api.response.SuccessResponse;
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
