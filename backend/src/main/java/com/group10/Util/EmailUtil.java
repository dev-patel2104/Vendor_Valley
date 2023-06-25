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

@Component
public class EmailUtil {

    private static final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Autowired 
    private JavaMailSender javaMailSender;
    
    @Value("${spring.mail.username}")
    private String sender;

    // To send a simple email
    public void sendSimpleMail(EmailDetails email) throws MailSendException, MailAuthenticationException, MailParseException
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
 
            // Sending the mail
            javaMailSender.send(mailMessage);
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

    public static boolean isValidEmail(String emailID) {
        return emailID.matches(emailRegex);
    }


}
