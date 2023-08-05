package com.group10.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.group10.Model.EmailDetails;

/**
 * Utility class for email-related operations.
 */
@Component
@Slf4j
public class EmailUtil {

    private static final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Autowired 
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * Sends a simple email using the provided email details.
     *
     * @param email The details of the email to be sent.
     * @return True if the email was sent successfully, false otherwise.
     * @throws MailSendException If there was an error sending the email.
     * @throws MailAuthenticationException If there was an error authenticating the email service.
     * @throws MailParseException If there was an error parsing the email.
     */
    public boolean sendSimpleMail(EmailDetails email) throws MailSendException, MailAuthenticationException, MailParseException {
        try {
            log.debug("Sending a simple email to: " + email.getRecipient());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(email.getRecipient());
            mailMessage.setText(email.getMsgBody());
            mailMessage.setSubject(email.getSubject());

            javaMailSender.send(mailMessage);

            log.debug("Email sent successfully to: " + email.getRecipient());
            return true;
        } catch (MailSendException e) {
            log.error("Error sending email: " + e.getMessage());
            throw new MailSendException("Uh-Oh! Email couldn't be sent due to an error!");
        } catch (MailAuthenticationException e) {
            log.error("Email authentication error: " + e.getMessage());
            throw new MailAuthenticationException("Our email service is down! Try again later!");
        } catch (MailParseException e) {
            log.error("Email parse error: " + e.getMessage());
            throw new MailParseException("Improper email format!");
        }
    }

    /**
     * Checks if the given email ID is valid based on a regular expression pattern.
     *
     * @param emailID The email ID to be validated.
     * @return true if the email ID is valid, false otherwise.
     */
    public static boolean isValidEmail(String emailID) {
        log.debug("Validating email: " + emailID);

        boolean isValid = emailID.matches(emailRegex);

        if (isValid) {
            log.debug("Email is valid: " + emailID);
        } else {
            log.debug("Email is not valid: " + emailID);
        }

        return isValid;
    }
}
