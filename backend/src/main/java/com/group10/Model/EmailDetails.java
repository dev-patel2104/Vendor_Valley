package com.group10.Model;

import org.springframework.stereotype.Component;

/**
 * Represents the details of an email, including the recipient, message body, and subject.
 */
@Component
public class EmailDetails {
    
    private String recipient;
    private String msgBody;
    private String subject;
    
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getMsgBody() {
        return msgBody;
    }
    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
}