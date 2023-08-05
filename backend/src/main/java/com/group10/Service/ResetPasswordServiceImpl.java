package com.group10.Service;
import java.security.SecureRandom;
import java.sql.SQLException;

import com.group10.Repository.Interfaces.ICustomerRepository;
import com.group10.Repository.Interfaces.IResetPasswordRepository;
import com.group10.Service.Interfaces.IResetPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Exceptions.VerificationCodeExpiredException;
import com.group10.Constants.Constants;
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Exceptions.PasswordsCantBeSameException;
import com.group10.Model.EmailDetails;
import com.group10.Model.User;
import com.group10.Util.EmailUtil;

/**
 * Service class for resetting user passwords.
 */
@Service
@Slf4j
public class ResetPasswordServiceImpl implements IResetPasswordService {
    
    @Autowired
    private ICustomerRepository CustomerRepositoryImpl;

    @Autowired
    private IResetPasswordRepository resetPasswordRepository;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private EmailDetails emailDetails;
    
    /**
     * Checks if a user exists based on their email.
     *
     * @param email The email of the user to check.
     * @return The user object if it exists, otherwise null.
     * @throws SQLException If there is an error executing the SQL query.
     * @throws UserDoesntExistException If the user does not exist.
     */
    public User checkIfUserExists(String email) throws SQLException, UserDoesntExistException {
        User user = null;
        try {
            // Retrieve a user from the user repository based on their email
            user = CustomerRepositoryImpl.findByEmail(email);

            // Check if the user exists, and throw an exception if not found
            if (user == null) {
                throw new UserDoesntExistException("User Doesn't Exist!");
            }
        } catch (SQLException e) {
            // Log and rethrow the SQLException
            log.error("Error occurred while checking user existence: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return user;
    }

    /**
     * Generates a verification code for a user and sends it via email.
     *
     * @param user The user for whom the verification code is generated.
     * @return true if the verification code is successfully generated and sent, false otherwise.
     * @throws SQLException if there is an error accessing the database.
     * @throws MailAuthenticationException if there is an error authenticating the email server.
     * @throws MailSendException if there is an error sending the email.
     * @throws MailParseException if there is an error parsing the email.
     */
    public boolean generateVerificationCode(User user) throws SQLException, MailAuthenticationException, MailSendException, MailParseException {
        SecureRandom rand = new SecureRandom();
        int code = rand.nextInt(Constants.VERIFICATIONCODEBOUND) + Constants.VARIATION;
        int userId = user.getUserId();
        if (userId == Constants.USERDOESNTEXIST) {
            log.warn("Attempted to generate verification code for non-existing user");
            return false;
        }
        String email = user.getEmail();
        boolean result;
        try {
            // Store the verification code for the user's password reset request
            result = resetPasswordRepository.storeVerificationCode(userId, code);
            if (result) {
                // Send the verification code to the user's email address
                sendVerificationCode(email, code);
            }
            return true;
        } catch (SQLException e) {
            log.error("Error occurred while generating verification code: " + e.getMessage());
            throw new SQLException(e.getMessage());
        } catch (MailAuthenticationException | MailSendException | MailParseException e) {
            log.error("Error occurred while sending email: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Sends a verification code to the specified email address for password reset.
     *
     * @param email The email address to send the verification code to.
     * @param code The verification code to be sent.
     * @throws MailAuthenticationException If there is an issue with the email authentication.
     * @throws MailSendException If there is an issue with sending the email.
     * @throws MailParseException If there is an issue with parsing the email.
     */
    public void sendVerificationCode(String email, int code) throws MailAuthenticationException, MailSendException, MailParseException {
        String subject = "Password Reset Request";
        String body = "Verification Code for resetting password is " + code;

        emailDetails.setRecipient(email);
        emailDetails.setSubject(subject);
        emailDetails.setMsgBody(body);

        try {
            // Send the verification code email
            emailUtil.sendSimpleMail(emailDetails);
            log.info("Verification code email sent successfully to: " + email);
        } catch (MailAuthenticationException | MailSendException | MailParseException e) {
            log.error("Error occurred while sending verification code email: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verifies the entered code against the stored verification code for a given email.
     *
     * @param email The email associated with the verification code
     * @param enteredCode The code entered by the user
     * @throws IllegalArgumentException If the entered code is incorrect
     * @throws NoInformationFoundException If no information is found for the given email
     * @throws VerificationCodeExpiredException If the verification code has expired
     * @throws SQLException If there is an error accessing the database
     */
    public void verifyCode(String email, String enteredCode) throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException {
        try {
            // Retrieve the verification code from the reset password repository
            int code = resetPasswordRepository.getVerificationCode(email);

            if (code == Constants.VERIFICATIONCODEEXPIRED) {
                throw new VerificationCodeExpiredException("Oops! The verification code has expired!");
            }
            if (code == -1) {
                throw new NoInformationFoundException("There seems to be no such information with us!");
            }
            if (Integer.parseInt(enteredCode) != code) {
                throw new IllegalArgumentException("You entered the wrong code, try again!");
            }

            log.info("Verification code verified successfully for email: " + email);
        } catch (SQLException e) {
            log.error("Error occurred while verifying verification code: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * Updates the password for a user with the given email.
     *
     * @param email The email of the user.
     * @param newPassword The new password to set.
     * @throws SQLException If there is an error accessing the database.
     * @throws UserDoesntExistException If the user does not exist.
     * @throws PasswordsCantBeSameException If the new password is the same as the old password.
     */
    public void updatePassword(String email, String newPassword) throws SQLException, UserDoesntExistException, PasswordsCantBeSameException {
        try {
            // Check if the user exists and retrieve the user object
            User user = checkIfUserExists(email);

            if (newPassword.equals(user.getPassword())) {
                throw new PasswordsCantBeSameException("Your new password cannot be your old password!");
            }

            // Update user object's password
            user.setPassword(newPassword);
            // Update the password in the database
            CustomerRepositoryImpl.updateUser(user);

            log.info("Password updated successfully for user: " + email);
        } catch (SQLException e) {
            log.error("Error occurred while updating password: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }
}
