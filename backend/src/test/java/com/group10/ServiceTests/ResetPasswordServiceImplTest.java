package com.group10.ServiceTests;

import com.group10.Repository.Interfaces.ICustomerRepository;
import com.group10.Repository.Interfaces.IResetPasswordRepository;
import com.group10.Service.Interfaces.IResetPasswordService;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;

import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Exceptions.PasswordsCantBeSameException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Exceptions.VerificationCodeExpiredException;
import com.group10.Model.EmailDetails;
import com.group10.Model.User;
import com.group10.Util.EmailUtil;

@SpringBootTest
public class ResetPasswordServiceImplTest {
    
    @MockBean 
    private ICustomerRepository CustomerRepositoryImpl;
    
    @MockBean 
    private IResetPasswordRepository resetPasswordRepository;

    @MockBean
    private EmailUtil emailUtil;

    @Autowired
    private User user;

    @Autowired
    private EmailDetails emailDetails;

    @Autowired
    private IResetPasswordService resetPasswordService;

    @Test
    public void successPath_checkIfUserExists() throws SQLException, UserDoesntExistException{
        String email = "test@gmail.com";
        Mockito.doReturn(user).when(CustomerRepositoryImpl).findByEmail(email);
        assertEquals(user, resetPasswordService.checkIfUserExists(email));
    }

    @Test
    public void testUserDoesntExist_checkIfUserExists() throws SQLException{
        String email = "test@gmail.com";
        Mockito.doReturn(null).when(CustomerRepositoryImpl).findByEmail(email);
        assertThrows(UserDoesntExistException.class,() -> resetPasswordService.checkIfUserExists(email));
    }

    @Test
    public  void testSQLException_checkIfUserExists() throws SQLException{
        String email = "test@gmail.com";
        Mockito.doThrow(new SQLException("Db Connection Lost!")).when(CustomerRepositoryImpl).findByEmail(email);
        assertThrows(SQLException.class,() -> resetPasswordService.checkIfUserExists(email));
    }

    @Test
    public void successPath_generateVerificationCode() throws SQLException, MailAuthenticationException, MailSendException, MailParseException {
        user.setUserId(1);
        user.setEmail("test@gmail.com");
        Mockito.doReturn(true).when(resetPasswordRepository).storeVerificationCode(anyInt(), anyInt());
        assertTrue(resetPasswordService.generateVerificationCode(user));
    }

    @Test
    public void testSQLException_generateVerificationCode() throws SQLException, MailAuthenticationException, MailSendException, MailParseException {
        user.setUserId(1);
        user.setEmail("test@gmail.com");
        Mockito.doThrow(new SQLException("Db Connection Lost!")).when(resetPasswordRepository).storeVerificationCode(anyInt(), anyInt());
        assertThrows(SQLException.class, () -> resetPasswordService.generateVerificationCode(user));
    }

    @Test
    public void successPath_sendVerificationCode() throws MailAuthenticationException, MailSendException, MailParseException {
        String email = "test@gmail.com";
        int code = 123456;
        emailDetails.setSubject("subject");
        emailDetails.setMsgBody("body"+code);
        emailDetails.setRecipient(email);
        resetPasswordService.sendVerificationCode(email, code);
        verify(emailUtil, times(1)).sendSimpleMail(emailDetails);
    }

    @Test
    public void testMailAuthenticationException_sendVerificationCode() throws MailAuthenticationException, MailSendException, MailParseException {
        String email = "test@gmail.com";
        int code = 123456;
        emailDetails.setSubject("subject");
        emailDetails.setMsgBody("body"+code);
        emailDetails.setRecipient(email);
        Mockito.doThrow(new MailAuthenticationException("Mail Authentication Failed!")).when(emailUtil).sendSimpleMail(emailDetails);
        assertThrows(MailAuthenticationException.class, () -> resetPasswordService.sendVerificationCode(email, code));
    }

    @Test
    public void testMailSendException_sendVerificationCode() throws MailAuthenticationException, MailSendException, MailParseException {
        String email = "test@gmail.com";
        int code = 123456;
        emailDetails.setSubject("subject");
        emailDetails.setMsgBody("body"+code);
        emailDetails.setRecipient(email);
        Mockito.doThrow(new MailSendException("Mail Send Failed!")).when(emailUtil).sendSimpleMail(emailDetails);
        assertThrows(MailSendException.class, () -> resetPasswordService.sendVerificationCode(email, code));
    }

    @Test
    public void testMailParseException_sendVerificationCode() throws MailAuthenticationException, MailSendException, MailParseException {
        String email = "test@gmail.com";
        int code = 123456;
        emailDetails.setSubject("subject");
        emailDetails.setMsgBody("body"+code);
        emailDetails.setRecipient(email);
        Mockito.doThrow(new MailParseException("Mail Parse Failed!")).when(emailUtil).sendSimpleMail(emailDetails);
        assertThrows(MailParseException.class, () -> resetPasswordService.sendVerificationCode(email, code));
    }

    @Test
    public void successPath_verifyCode() throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException {
        String email = "test@gmail.com";
        String enteredCode = "123456";
        Mockito.doReturn(123456).when(resetPasswordRepository).getVerificationCode(email);
        resetPasswordService.verifyCode(email, enteredCode);
    }

    @Test
    public void testIllegalArgumentException_verifyCode() throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException {
        String email = "test@gmail.com";
        String enteredCode = "654321";
        Mockito.doReturn(123456).when(resetPasswordRepository).getVerificationCode(email);
        assertThrows(IllegalArgumentException.class, () -> resetPasswordService.verifyCode(email, enteredCode));
    }

    @Test
    public void testNoInformationFoundException_verifyCode() throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException {
        String email = "test@gmail.com";
        String enteredCode = "123456";
        Mockito.doReturn(-1).when(resetPasswordRepository).getVerificationCode(email);
        assertThrows(NoInformationFoundException.class, () -> resetPasswordService.verifyCode(email, enteredCode));
    }

    @Test
    public void testVerificationCodeExpired_verifyCode() throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException {
        String email = "test@gmail.com";
        String enteredCode = "123456";
        Mockito.doReturn(0).when(resetPasswordRepository).getVerificationCode(email);
        assertThrows(VerificationCodeExpiredException.class, () -> resetPasswordService.verifyCode(email, enteredCode));
    }

    @Test
    public void testSQLException_verifyCode() throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException {
        String email = "test@gmail.com";
        String enteredCode = "123456";
        Mockito.doThrow(new SQLException("Db Connection Lost!")).when(resetPasswordRepository).getVerificationCode(email);
        assertThrows(SQLException.class, () -> resetPasswordService.verifyCode(email, enteredCode));
    }

    @Test
    public void successPath_updatePassword() throws SQLException, UserDoesntExistException, PasswordsCantBeSameException {
        String email = "test@gmail.com";
        String newPassword = "newpass123";
        user.setEmail(email);
        user.setPassword("oldpass456");
        Mockito.doReturn(user).when(CustomerRepositoryImpl).findByEmail(email);
        Mockito.doReturn(true).when(CustomerRepositoryImpl).updateUser(user);
        resetPasswordService.updatePassword(email, newPassword);
    }

    @Test
    public void testSQLException_updatePassword() throws SQLException, UserDoesntExistException, PasswordsCantBeSameException {
        String email = "test@gmail.com";
        String newPassword = "newpass123";
        user.setEmail(email);
        user.setPassword("oldpass456");
        Mockito.doReturn(user).when(CustomerRepositoryImpl).findByEmail(email);
        Mockito.doThrow(new SQLException("Db Connection Lost!")).when(CustomerRepositoryImpl).updateUser(user);
        assertThrows(SQLException.class, () -> resetPasswordService.updatePassword(email, newPassword));
    }

    @Test
    public void testUserDoesntExistException_updatePassword() throws SQLException, UserDoesntExistException, PasswordsCantBeSameException {
        String email = "test@gmail.com";
        String newPassword = "newpass123";
        Mockito.doReturn(null).when(CustomerRepositoryImpl).findByEmail(email);
        assertThrows(UserDoesntExistException.class, () -> resetPasswordService.updatePassword(email, newPassword));
    }

    @Test
    public void testPasswordsCantBeSameException_updatePassword() throws SQLException, UserDoesntExistException, PasswordsCantBeSameException {
        String email = "test@gmail.com";
        String newPassword = "oldpass456";
        user.setEmail(email);
        user.setPassword("oldpass456");
        Mockito.doReturn(user).when(CustomerRepositoryImpl).findByEmail(email);
        assertThrows(PasswordsCantBeSameException.class, () -> resetPasswordService.updatePassword(email, newPassword));
    }

}
