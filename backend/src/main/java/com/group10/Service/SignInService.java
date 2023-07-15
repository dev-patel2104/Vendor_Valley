package com.group10.Service;

import com.group10.Constants.IntegerConstants;
import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Exceptions.VendorDetailsAbsentForUserException;
import com.group10.Model.SignUpModel;
import com.group10.Model.User;
import com.group10.Model.Vendor;
import com.group10.Repository.VendorRepository;
import com.group10.Repository.UserRepository;
import com.group10.Util.EmailUtil;
import com.group10.Util.PasswordUtil;
import com.group10.Util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class SignInService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    private final int IS_VENDOR = 1;
    private final int IS_NOT_VENDOR = 0;

    public boolean SignIn(SignUpModel signUpModel) throws UserAlreadyPresentException, SQLException, VendorDetailsAbsentForUserException {
        if (signUpModel == null) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getFirstName())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getLastName())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getMobile())) {
            return false;
        }
        if(signUpModel.getIsVendor() != IS_NOT_VENDOR && signUpModel.getIsVendor() != IS_VENDOR) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getStreet())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getCity())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getProvince())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getCountry())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getEmail())) {
            return false;
        }
        if(!PasswordUtil.isValidPassword(signUpModel.getPassword())) {
            return false;
        }

        if(!EmailUtil.isValidEmail(signUpModel.getEmail())) {
            return false;
        }
        int userId=0;
        User userModel = signUpModel.buildUserModel();
        Vendor vendorModel = signUpModel.buildVendorModel();

        if(signUpModel.getIsVendor() == IS_VENDOR)
        {
            if(vendorRepository.saveVendor(userModel, vendorModel))
            {
                return true;
            }
            else {
                throw new VendorDetailsAbsentForUserException("User details saved to db, but vendor details are missing in database");
            }

        }
        else
        {
            userId = userRepository.addUser(userModel);
            if(userId==IntegerConstants.userAlreadyExists)
            {
                throw new UserAlreadyPresentException("The user is already present");
            }
            else if(userId==IntegerConstants.userNotInserted)
            {
                return false;
            }
            return true;
        }

    }
}
