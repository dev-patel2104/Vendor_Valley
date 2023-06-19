package com.group10.UtilTests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.group10.Model.EmailDetails;
import com.group10.Util.EmailUtil;

@SpringBootTest
public class EmailUtilTest {
    
    @Autowired
    private EmailUtil emailUtil;

    @MockBean
    private JavaMailSender javaMailSender;
    
    @Test
    public void successPath_sendSimpleMail() throws MailSendException, MailAuthenticationException, MailParseException {
        EmailDetails email = new EmailDetails();
        email.setRecipient("test@gmail.com");
        email.setSubject("Test Subject");
        email.setMsgBody("Test Body");
        emailUtil.sendSimpleMail(email);
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testMailSendException_sendSimpleMail() throws MailSendException, MailAuthenticationException, MailParseException {
        EmailDetails email = new EmailDetails();
        email.setRecipient("test@gmail.com");
        email.setSubject("Test Subject");
        email.setMsgBody("Test Body");
        doThrow(new MailSendException("Mail Send Failed!")).when(javaMailSender).send(any(SimpleMailMessage.class));
        assertThrows(MailSendException.class, () -> emailUtil.sendSimpleMail(email));
    }

    @Test
    public void testMailAuthenticationException_sendSimpleMail() throws MailSendException, MailAuthenticationException, MailParseException {
        EmailDetails email = new EmailDetails();
        email.setRecipient("test@gmail.com");
        email.setSubject("Test Subject");
        email.setMsgBody("Test Body");
        doThrow(new MailAuthenticationException("Mail Authentication Failed!")).when(javaMailSender).send(any(SimpleMailMessage.class));
        assertThrows(MailAuthenticationException.class, () -> emailUtil.sendSimpleMail(email));
    }

    @Test
    public void testMailParseException_sendSimpleMail() throws MailSendException, MailAuthenticationException, MailParseException {
        EmailDetails email = new EmailDetails();
        email.setRecipient("test@gmail.com");
        email.setSubject("Test Subject");
        email.setMsgBody("Test Body");
        doThrow(new MailParseException("Mail Parse Failed!")).when(javaMailSender).send(any(SimpleMailMessage.class));
        assertThrows(MailParseException.class, () -> emailUtil.sendSimpleMail(email));
    }
}
