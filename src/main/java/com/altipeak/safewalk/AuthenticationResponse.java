package com.altipeak.safewalk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthenticationResponse {

    private final AuthenticationCode code;
    private final String transactionId;
    private final String username;
    private final String replyMessage;
    
    private final int httpCode;
    private final String detail;
    
    private Map<String, List<String>> errors;
    
    private String SEPARATOR = " | ";

    public enum AuthenticationCode {
        ACCESS_ALLOWED,
        ACCESS_CHALLENGE,
        ACCESS_DENIED;
    }
    
    public AuthenticationResponse(AuthenticationCode code
                                , String transactionId
                                , String username
                                , String replyMessage
                                , int httpCode
                                , String detail) {
        this.code = code;
        this.transactionId = transactionId;
        this.username = username;
        this.replyMessage = replyMessage;
        this.httpCode = httpCode;
        this.detail = detail;
    }
    
    // ************************************
    // * Public Methods
    // ************************************
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(this.httpCode)).append(SEPARATOR);
        if ( this.code != null ) sb.append(this.code).append(SEPARATOR);
        if ( this.transactionId != null ) sb.append(this.transactionId).append(SEPARATOR);
        if ( this.username != null ) sb.append(this.username).append(SEPARATOR);
        if ( this.replyMessage != null ) sb.append(this.replyMessage).append(SEPARATOR);
        if ( this.detail != null ) sb.append(this.detail).append(SEPARATOR);
        return sb.substring(0, sb.length() - SEPARATOR.length()).toString();
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public AuthenticationCode getCode() {
        return code;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getUsername() {
        return username;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getDetail() {
        return detail;
    }
    
}
