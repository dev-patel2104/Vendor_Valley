package com.group10.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.group10.Exceptions.InvalidPasswordException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.User;
import com.group10.Service.LoginService;
import com.group10.Util.JWTTokenHandler;

import java.sql.SQLException;
import java.util.Map;

@RestController
public class LoginController {

    @Value("${secret.key}")
    private String secretKey;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JWTTokenHandler tokenHandler;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        boolean credentialsMapCheck = credentials == null || credentials.size() == 0;
        if (credentialsMapCheck){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        boolean emailCheck = !credentials.containsKey("email") ||  credentials.get("email").equals("");
        boolean passwordCheck = !credentials.containsKey("password") || credentials.get("password").equals("");
        if (emailCheck || passwordCheck){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        String email = credentials.get("email");
        String password = credentials.get("password");
        try{
            // Login
            User user = loginService.login(email, password);
            // Encode details in JWT token
            String token = tokenHandler.generateJWTToken(user);
            // Return
            return ResponseEntity.ok(token);
        }
        catch(UserDoesntExistException | InvalidPasswordException e){
            // Login failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch(SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}