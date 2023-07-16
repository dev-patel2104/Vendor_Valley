package com.group10.Service;
import java.security.SecureRandom;
import java.sql.SQLException;
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
import com.group10.Repository.ResetPasswordRepository;
import com.group10.Repository.UserRepository;
import com.group10.Util.EmailUtil;

/**
 * Service class for resetting user passwords.
 */
@Service
public class ResetPasswordService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

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
        try{
            /**
             * Retrieves a user from the user repository based on their email.
             *
             * @param email The email of the user to retrieve.
             * @return The user entity associated with the given email, or null if no user is found.
             */
            user = userRepository.findByEmail(email);
            if (user == null) {
               throw new UserDoesntExistException("User Doesn't Exists!");
            }
        }
        catch (SQLException e){
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
        int code = rand.nextInt(900000) + 100000;
        int userId = user.getUserId();
        if (userId == Constants.USERDOESNTEXIST){
            return false;
        }
        String email = user.getEmail();
        boolean result;
        try {
            /**
             * Stores the verification code for a user's password reset request.
             *
             * @param userId The ID of the user requesting the password reset
             * @param code The verification code to be stored
             * @return The result of the storage operation
             */
            result = resetPasswordRepository.storeVerificationCode(userId, code);
            if (result){
                /**
                 * Sends a verification code to the specified email address.
                 *
                 * @param email The email address to send the verification code to.
                 * @param code The verification code to send.
                 */
                sendVerificationCode(email, code);
            }
            return true;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        catch (MailAuthenticationException e) {
           throw new MailAuthenticationException(e.getMessage());
        }
        catch (MailSendException e) {
           throw new MailSendException(e.getMessage());
        }
        catch (MailParseException e) {
           throw new MailSendException(e.getMessage());
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
        String body = "Verficiation Code for resetting password is ";
        body = body + code;
        
        emailDetails.setRecipient(email);
        emailDetails.setSubject(subject);
        emailDetails.setMsgBody(body);
        try {
            /**
             * Sends a simple email using the provided email details.
             *
             * @param emailDetails The details of the email to be sent
             */
            emailUtil.sendSimpleMail(emailDetails);
        }
        catch (MailAuthenticationException e) {
           throw new MailAuthenticationException(e.getMessage());
        }
        catch (MailSendException e) {
           throw new MailSendException(e.getMessage());
        }
        catch (MailParseException e) {
           throw new MailParseException(e.getMessage());
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
            /**
             * Retrieves the verification code associated with the given email from the reset password repository.
             *
             * @param email The email for which to retrieve the verification code.
             * @return The verification code associated with the email.
             */
            int code = resetPasswordRepository.getVerificationCode(email);
            if (code==0){
                throw new VerificationCodeExpiredException("Oops! The verification code has expired!");
            }
            if (code == -1){
                throw new NoInformationFoundException("There seems to be no such information with us!");
            }
            if(Integer.parseInt(enteredCode) != code){
                throw new IllegalArgumentException("You entered the wrong code, try again!");
            }
        } catch (SQLException e) {
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
        
        try{
            /**
             * Checks if a user with the given email exists in the system.
             *
             * @param email The email of the user to check
             * @return The User object if the user exists, null otherwise
             */
            User user = checkIfUserExists(email);
            if(newPassword.equals(user.getPassword())){
                throw new PasswordsCantBeSameException("Your new password cannot be your old password!");
            }
            // update user object's password
            user.setPassword(newPassword);
            // update the same in db
            userRepository.updateUser(user);
        }
        catch(SQLException e){
           throw new SQLException(e.getMessage());
        }
    }
}
