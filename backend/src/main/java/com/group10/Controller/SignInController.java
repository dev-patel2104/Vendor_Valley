package com.group10.Controller;

import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Exceptions.VendorDetailsAbsentForUserException;
import com.group10.Model.SignUpModel;
import com.group10.Service.SignInService;
import com.group10.Util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


@RestController
public class SignInController {


    @Autowired
    private SignInService signInService;

    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/register")
    public ResponseEntity<String> signIn(@RequestBody SignUpModel signUpModel) {

        if (StringUtil.isNotNullAndNotEmpty(signUpModel.getEmail())) {
            try {
                if (!signInService.SignIn(signUpModel)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
                }
            } catch (SQLException | VendorDetailsAbsentForUserException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            } catch (UserAlreadyPresentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } catch(Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
            }
        }

        return ResponseEntity.ok("User has been added successfully");
    }
}
