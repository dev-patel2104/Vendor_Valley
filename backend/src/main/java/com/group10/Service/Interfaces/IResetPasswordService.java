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

/**
 * Interface for reset password-related services.
 */
public interface IResetPasswordService {

    /**
     * Checks if a user with the given email exists.
     *
     * @param email The email of the user to check.
     * @return The User object if user exists, else null.
     * @throws SQLException            If a database error occurs.
     * @throws UserDoesntExistException If the user doesn't exist.
     */
    User checkIfUserExists(String email) throws SQLException, UserDoesntExistException;

    /**
     * Generates a verification code for password reset and sends it to the user via email.
     *
     * @param user The user for whom the verification code is generated.
     * @return True if code generation and sending are successful, else false.
     * @throws SQLException               If a database error occurs.
     * @throws MailAuthenticationException If mail authentication fails.
     * @throws MailSendException          If there is an issue sending the mail.
     * @throws MailParseException         If the mail content cannot be parsed.
     */
    boolean generateVerificationCode(User user) throws SQLException, MailAuthenticationException, MailSendException, MailParseException;

    /**
     * Sends the verification code to the provided email address.
     *
     * @param email The email address to which the code is sent.
     * @param code  The verification code.
     * @throws MailAuthenticationException If mail authentication fails.
     * @throws MailSendException          If there is an issue sending the mail.
     * @throws MailParseException         If the mail content cannot be parsed.
     */
    void sendVerificationCode(String email, int code) throws MailAuthenticationException, MailSendException, MailParseException;

    /**
     * Verifies the entered verification code for a given email.
     *
     * @param email       The email for which verification is done.
     * @param enteredCode The verification code entered by the user.
     * @throws IllegalArgumentException    If the entered code is invalid.
     * @throws NoInformationFoundException   If there is no code for the email.
     * @throws VerificationCodeExpiredException If the verification code has expired.
     * @throws SQLException                  If a database error occurs.
     */
    void verifyCode(String email, String enteredCode) throws IllegalArgumentException,
            NoInformationFoundException, VerificationCodeExpiredException, SQLException;

    /**
     * Updates the password for a user.
     *
     * @param email       The email of the user.
     * @param newPassword The new password.
     * @throws SQLException               If a database error occurs.
     * @throws UserDoesntExistException   If the user doesn't exist.
     * @throws PasswordsCantBeSameException If the new password is the same as the old password.
     */
    void updatePassword(String email, String newPassword) throws SQLException, UserDoesntExistException,
            PasswordsCantBeSameException;
}
