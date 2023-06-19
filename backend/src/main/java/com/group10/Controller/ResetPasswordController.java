package com.group10.Controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Exceptions.PasswordsCantBeSameException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Exceptions.VerificationCodeExpiredException;
import com.group10.Model.User;
import com.group10.Service.ResetPasswordService;

@RestController
public class ResetPasswordController {
    
    @Autowired
    private ResetPasswordService resetPasswordService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> credentials) {
        boolean credentialsMapCheck = credentials == null || credentials.size() == 0;
        if (credentialsMapCheck){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        } 
        boolean emailCheck = !credentials.containsKey("email") ||  credentials.get("email").equals("");
        if (emailCheck){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        String email = credentials.get("email");
        try{
            User user = resetPasswordService.checkIfUserExists(email);       
            // call method to generate random code and send email
            if(user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Doesn't Exists!");            
            }
            boolean success = resetPasswordService.generateVerificationCode(user);
            if(!success){
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Email couldn't be sent! Try again later!");
            }
        }
        catch(UserDoesntExistException e){
            // Login failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch(SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        catch(MailSendException | MailAuthenticationException | MailParseException e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
        return ResponseEntity.ok("Verification Code will be sent to your email shortly!");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/verifyConfirmationCode")
    public ResponseEntity<String> verifyConfirmationCode(@RequestBody Map<String, String> credentials) {
        boolean credentialsMapCheck = credentials == null || credentials.size() == 0;
        if (credentialsMapCheck){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        boolean codeCheck = !credentials.containsKey("code") ||  credentials.get("code").equals("");
        boolean emailCheck = !credentials.containsKey("email") ||  credentials.get("email").equals("");
        if (codeCheck || emailCheck){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        String email = credentials.get("email");
        String code = credentials.get("code");
        try{
            resetPasswordService.verifyCode(email, code);
        }
        catch(SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } 
        catch (NoInformationFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } 
        catch (VerificationCodeExpiredException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok("Success! You can now change update your password!");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> credentials) {
        boolean credentialsMapCheck = credentials == null || credentials.size() == 0;
        if (credentialsMapCheck){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        boolean emailCheck = !credentials.containsKey("email") ||  credentials.get("email").equals("");
        boolean passwordCheck = !credentials.containsKey("password") ||  credentials.get("password").equals("");
        if (passwordCheck || emailCheck){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        String email = credentials.get("email");
        String newPassword = credentials.get("password");
        try{
            resetPasswordService.updatePassword(email, newPassword);
        }
        catch(SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } 
        catch (UserDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } 
        catch (PasswordsCantBeSameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("Successfully updated your password! You can login now with your updated password!");
    }
}
