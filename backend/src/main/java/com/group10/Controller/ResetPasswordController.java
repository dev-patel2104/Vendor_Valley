package com.group10.Controller;

import java.sql.SQLException;
import java.util.Map;

import com.group10.Service.Interfaces.IResetPasswordService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("Received request to initiate forgot password process");

        if (credentials == null) {
            log.warn("Invalid request: credentials are null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }

        boolean credentialsMapCheck = credentials.size() == 0;
        if (credentialsMapCheck) {
            log.warn("Invalid request: credentials map is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }

        boolean emailCheck = !credentials.containsKey("email") || credentials.get("email").equals("");
        if (emailCheck) {
            log.warn("Invalid request: email is missing or empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }

        String email = credentials.get("email");
        try {
            User user = resetPasswordService.checkIfUserExists(email);

            if (user == null) {
                log.warn("User not found for email: {}", email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Doesn't Exists!");
            }

            boolean success = resetPasswordService.generateVerificationCode(user);
            if (!success) {
                log.error("Email couldn't be sent for verification code. Try again later.");
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Email couldn't be sent! Try again later!");
            }
        } catch (UserDoesntExistException e) {
            log.warn("User not found for email: {}", email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (SQLException e) {
            log.error("SQLException occurred while processing request.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (MailSendException | MailAuthenticationException | MailParseException e) {
            log.error("Exception occurred while sending email.", e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }

        log.info("Verification code will be sent to the email shortly.");
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
        log.info("Received request to verify confirmation code");

        if (credentials == null) {
            log.warn("Invalid request: credentials are null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }

        boolean credentialsMapCheck = credentials.size() == 0;
        if (credentialsMapCheck) {
            log.warn("Invalid request: credentials map is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }

        boolean codeCheck = !credentials.containsKey("code") || credentials.get("code").equals("");
        boolean emailCheck = !credentials.containsKey("email") || credentials.get("email").equals("");
        if (codeCheck || emailCheck) {
            log.warn("Invalid request: email or code is missing or empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }

        String email = credentials.get("email");
        String code = credentials.get("code");
        try {
            log.info("Verifying confirmation code for email: {}", email);
            resetPasswordService.verifyCode(email, code);
        } catch (SQLException e) {
            log.error("SQLException occurred while processing request.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoInformationFoundException e) {
            log.warn("No information found for email: {}", email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (VerificationCodeExpiredException | IllegalArgumentException e) {
            log.warn("Verification code expired or invalid for email: {}", email);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }

        log.info("Verification code verified successfully for email: {}", email);
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
        log.info("Received request to update password");

        if (credentials == null) {
            log.warn("Invalid request: credentials are null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }

        boolean credentialsMapCheck = credentials.size() == 0;
        if (credentialsMapCheck) {
            log.warn("Invalid request: credentials map is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }

        boolean emailCheck = !credentials.containsKey("email") || credentials.get("email").equals("");
        boolean passwordCheck = !credentials.containsKey("password") || credentials.get("password").equals("");
        if (passwordCheck || emailCheck) {
            log.warn("Invalid request: email or password is missing or empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }

        String email = credentials.get("email");
        String newPassword = credentials.get("password");
        try {
            log.info("Updating password for email: {}", email);
            resetPasswordService.updatePassword(email, newPassword);
        } catch (SQLException e) {
            log.error("SQLException occurred while processing request.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (UserDoesntExistException e) {
            log.warn("User doesn't exist for email: {}", email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (PasswordsCantBeSameException e) {
            log.warn("Old and new passwords cannot be the same for email: {}", email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        log.info("Password updated successfully for email: {}", email);
        return ResponseEntity.ok("Successfully updated your password! You can login now with your updated password!");
    }
}
