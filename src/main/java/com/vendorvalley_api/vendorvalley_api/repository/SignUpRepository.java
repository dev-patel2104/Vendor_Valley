package com.vendorvalley_api.vendorvalley_api.repository;

import com.vendorvalley_api.vendorvalley_api.model.SignUpModel;
import org.springframework.stereotype.Repository;

@Repository
public class SignUpRepository {

    public boolean saveVendor(SignUpModel signUpModel) {
        return true;
    }
}
