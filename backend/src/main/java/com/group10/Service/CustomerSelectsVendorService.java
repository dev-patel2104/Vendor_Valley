package com.group10.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Model.EmailDetails;
import com.group10.Model.Review;
import com.group10.Model.Service;
import com.group10.Model.User;
import com.group10.Repository.ServiceRepository;
import com.group10.Repository.UserRepository;
import com.group10.Service.Interfaces.ICustomerSelectsVendorService;
import com.group10.Util.EmailUtil;
import com.group10.Util.JWTTokenHandler;

@org.springframework.stereotype.Service
public class CustomerSelectsVendorService implements ICustomerSelectsVendorService{

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTTokenHandler jwtTokenHandler;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private EmailDetails emailDetails;


    @Override
    public List<Review> getReviews(String serviceId) throws Exception {
        try{

            int serviceIdInt = Integer.parseInt(serviceId);
            boolean exists = checkIfServiceExists(serviceIdInt);
            if (!exists){
                return new ArrayList<>();
            }
            // Call repository to get reviews
            return serviceRepository.getReviewsForService(serviceIdInt);
        }
        catch (NumberFormatException e){
            throw new NumberFormatException("Not a valid service id");
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private boolean checkIfServiceExists(int serviceId) throws SQLException {
        boolean exists = false;
        try{
            exists = serviceRepository.checkIfServiceExists(serviceId);
        }
        catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
        return exists;
    }

    @Override
    public boolean writeReviews(Review review, String JWTToken) throws SQLException, JWTVerificationException {
        boolean result = false;
        // Decode JWT Token
        DecodedJWT jwt = jwtTokenHandler.decodeJWTToken(JWTToken);
        // Get user id from JWT Token
        int userId = jwt.getClaim("userId").asInt();
        // Set user id in review object
        review.setReviewerId(userId);
        // Create review dateTime
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        // Set review dateTime
        review.setReviewDate(formattedDateTime);
        // Call repository to write reviews
        result = serviceRepository.writeReviews(review);
        return result;
    }

    @Override
    public boolean sendEmail(String serviceId, String emailText, String JWTToken) throws MailAuthenticationException, MailSendException, MailParseException, SQLException, JWTVerificationException{
        if (serviceId == null || serviceId.equals("") || JWTToken == null || JWTToken.equals("") || emailText == null || emailText.equals(""))
        {
            return false;
        }
        boolean result = false;
        // Decode JWT Token
        DecodedJWT jwt = jwtTokenHandler.decodeJWTToken(JWTToken);
        // Get user email from JWT Token
        String senderEmail = jwt.getClaim("email").asString(); 
        // Get user name from user email
        User user = userRepository.findByEmail(senderEmail);
        String name = user.getFullName();
        // Get service name from service id
        int serviceIdInt = Integer.parseInt(serviceId);
        Service service = serviceRepository.getServiceDetails(serviceIdInt);
        if (service == null || user == null){
            return false;
        }
        // Create email details object
        emailDetails.setSubject("You have a new message from " + name + " regarding your service " + service.getServiceName() + "!");
        emailDetails.setMsgBody(emailText +"\n\n" + "Sent by: " + senderEmail);
        emailDetails.setRecipient(service.getCompanyEmail());
        // Send email
        result = emailUtil.sendSimpleMail(emailDetails);
        if (!result){
            return false;
        }
        return true;
    }

}
