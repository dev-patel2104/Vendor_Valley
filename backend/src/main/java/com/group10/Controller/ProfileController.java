package com.group10.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.SignUpModel;
import com.group10.Service.CustomerProfileService;
import com.group10.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class ProfileController
{

    @Autowired
    @Qualifier("CustomerProfileService")
    private ProfileService customerProfileService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/profile")
    public ResponseEntity<SignUpModel> getProfile(@RequestParam int id)
    {
        /*DecodedJWT decodedJWT = JWT.decode(jwtToken);
        int id = decodedJWT.getClaim("user_id").asInt();*/
        SignUpModel user;
        try {
           user = customerProfileService.getProfile(id);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        catch (UserDoesntExistException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok().body(user);
    }
}
