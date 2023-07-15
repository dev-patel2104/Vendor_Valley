package com.group10.Util;
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
    public boolean sendSimpleMail(EmailDetails email) throws MailSendException, MailAuthenticationException, MailParseException
    {
        
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
 
            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(email.getRecipient());
            mailMessage.setText(email.getMsgBody());
            mailMessage.setSubject(email.getSubject());
 
            /**
             * Sends an email using the configured JavaMailSender.
             *
             * @param mailMessage The email message to send
             */
            javaMailSender.send(mailMessage);
            return true;
        }
 
        // Catch block to handle the exceptions
        catch (MailSendException e) {
            throw new MailSendException("Uh-Oh! Email couldn't be sent due to an error!");
        }
        catch (MailAuthenticationException e) {
            throw new MailAuthenticationException("Our email service is down! Try again later!");
        }
        catch (MailParseException e) {
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
        return emailID.matches(emailRegex);
    }

}
