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
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Exceptions.PasswordsCantBeSameException;
import com.group10.Model.EmailDetails;
import com.group10.Model.User;
import com.group10.Repository.ResetPasswordRepository;
import com.group10.Repository.UserRepository;
import com.group10.Util.EmailUtil;

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
    
    public User checkIfUserExists(String email) throws SQLException, UserDoesntExistException {
        User user = null;
        try{
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

    public boolean generateVerificationCode(User user) throws SQLException, MailAuthenticationException, MailSendException, MailParseException {
        SecureRandom rand = new SecureRandom();
        int code = rand.nextInt(900000) + 100000;
        int userId = user.getUserId();
        String email = user.getEmail();
        boolean result;
        try {
            result = resetPasswordRepository.storeVerificationCode(userId, code);
            if (result){
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

    public void sendVerificationCode(String email, int code) throws MailAuthenticationException, MailSendException, MailParseException {
        String subject = "Password Reset Request";
        String body = "Verficiation Code for resetting password is ";
        body = body + code;
        
        emailDetails.setRecipient(email);
        emailDetails.setSubject(subject);
        emailDetails.setMsgBody(body);
        try {
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

    public void verifyCode(String email, String enteredCode) throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException {
        try {
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

    public void  updatePassword(String email, String newPassword) throws SQLException, UserDoesntExistException, PasswordsCantBeSameException {
        
        try{
            // get user object
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
