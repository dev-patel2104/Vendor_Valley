package com.group10.Service.Interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;

import com.group10.Model.Review;

public interface ICustomerSelectsVendorService {
    public List<Review> getReviews(String serviceId) throws Exception;
    public boolean writeReviews(Review review, String JWTToken) throws Exception;
    public boolean sendEmail(String serviceId, String emailText, String JWTToken) throws MailAuthenticationException, MailSendException, MailParseException, SQLException;
}
