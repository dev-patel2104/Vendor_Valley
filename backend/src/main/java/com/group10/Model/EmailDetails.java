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

    /**
     * Get the recipient's email address.
     *
     * @return The recipient's email address.
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Set the recipient's email address.
     *
     * @param recipient The recipient's email address to set.
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Get the message body of the email.
     *
     * @return The message body.
     */
    public String getMsgBody() {
        return msgBody;
    }

    /**
     * Set the message body of the email.
     *
     * @param msgBody The message body to set.
     */
    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    /**
     * Get the subject of the email.
     *
     * @return The email subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the subject of the email.
     *
     * @param subject The email subject to set.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

}