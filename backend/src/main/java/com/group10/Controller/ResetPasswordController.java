package com.group10.Controller;

import java.sql.SQLException;
import java.util.Map;

import com.group10.Service.Interfaces.IResetPasswordService;
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

@RestController
public class ResetPasswordController {
    
    @Autowired
    private IResetPasswordService resetPasswordService;


    /**
     * Handles the forgot password request and sends a verification code to the user's email.
     *
     * @param credentials A map containing the user's credentials (email, username, etc.)
     * @return A ResponseEntity object with the appropriate HTTP status code and response body
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> credentials) {

        if (credentials == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        boolean credentialsMapCheck = credentials.size() == 0;
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

            if(user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Doesn't Exists!");            
            }
            /**
             * Generates a verification code for resetting the password of a user.
             *
             * @param user The user for whom the verification code is being generated.
             * @return true if the verification code was successfully generated, false otherwise.
             */
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

    /**
     * Verifies the confirmation code provided by the user.
     *
     * @param credentials A map containing the user's credentials, including the confirmation code
     * @return A ResponseEntity object with a status code and a message indicating the result of the verification
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/verifyConfirmationCode")
    public ResponseEntity<String> verifyConfirmationCode(@RequestBody Map<String, String> credentials) {

        if (credentials == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        boolean credentialsMapCheck = credentials.size() == 0;
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
            /**
             * Verifies the provided verification code for the given email address.
             *
             * @param email The email address associated with the verification code
             * @param code The verification code to be verified
             */
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

    /**
     * Updates the password for a user.
     *
     * @param credentials A map containing the user's credentials, including the old and new passwords.
     * @return A ResponseEntity with a status code and a message indicating the result of the password update.
     *         If the update is successful, the status code will be OK and the message will indicate success.
     *         If there is an error, the status code will indicate the type of error and the message will provide
     *         additional details about the error.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> credentials) {

        if (credentials == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        boolean credentialsMapCheck = credentials.size() == 0;
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
            /**
             * Updates the password for a user with the given email.
             *
             * @param email The email of the user whose password needs to be updated.
             * @param newPassword The new password to set for the user.
             */
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
