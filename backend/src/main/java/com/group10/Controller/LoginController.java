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

/**
 * Controller class for handling login requests.
 */
@RestController
public class LoginController {

    @Value("${secret.key}")
    private String secretKey;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JWTTokenHandler tokenHandler;

    /**
     * Handles the login request and returns a response entity with the appropriate status and body.
     *
     * @param credentials A map containing the login credentials
     * @return A response entity with the appropriate status and body
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {

        if (credentials == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        boolean credentialsMapCheck =  credentials.size() == 0;
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
            /**
             * Logs in a user with the provided email and password.
             *
             * @param email The email of the user
             * @param password The password of the user
             * @return The logged in user object
             */
            User user = loginService.login(email, password);

            /**
             * Generates a JWT token for the given user using the token handler.
             *
             * @param user The user for whom the token is being generated
             * @return The generated JWT token
             */
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