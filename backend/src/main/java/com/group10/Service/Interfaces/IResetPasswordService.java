package com.group10.Service.Interfaces;

import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Exceptions.PasswordsCantBeSameException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Exceptions.VerificationCodeExpiredException;
import com.group10.Model.User;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;

import java.sql.SQLException;

public interface IResetPasswordService
{
    public User checkIfUserExists(String email) throws SQLException, UserDoesntExistException;
    public boolean generateVerificationCode(User user) throws SQLException, MailAuthenticationException, MailSendException, MailParseException;
    public void sendVerificationCode(String email, int code) throws MailAuthenticationException, MailSendException, MailParseException;
    public void verifyCode(String email, String enteredCode) throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException;
    public void updatePassword(String email, String newPassword) throws SQLException, UserDoesntExistException, PasswordsCantBeSameException;
}
