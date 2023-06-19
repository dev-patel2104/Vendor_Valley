package com.group10.Controller;

import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Model.User;
import com.group10.Service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SignInController {

    private Map<String, User> userMap = new HashMap<>();

    @Autowired
    private SignInService signInService;

    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/register")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        userMap.put(user.getEmail(),user);
        if(userMap.containsKey(user.getEmail()))
        {
            try
            {
                signInService.SignIn(user);
            }
            catch (SQLException e)
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
            catch (UserAlreadyPresentException e)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            catch(Exception e){
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
            }

        }
        return ResponseEntity.ok("User has been added successfully");
    }
}
