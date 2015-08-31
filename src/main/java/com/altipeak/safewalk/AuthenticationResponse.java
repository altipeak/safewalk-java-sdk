package com.altipeak.safewalk;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AuthenticationResponse {

    private final AuthenticationCode code;
    private final String transactionId;
    private final String username;
    private final String replyMessage;
    
    private final int httpCode;
    private final String detail;
    
    private final Map<String, List<String>> errors;
    
    private static final String SEPARATOR = " | ";

    public enum AuthenticationCode {
        ACCESS_ALLOWED,
        ACCESS_CHALLENGE,
        ACCESS_DENIED;
    }
    
    public AuthenticationResponse(int httpCode
                                , AuthenticationCode code
                                , String transactionId
                                , String username
                                , String replyMessage
                                , String detail) {
        this.code = code;
        this.transactionId = transactionId;
        this.username = username;
        this.replyMessage = replyMessage;
        this.httpCode = httpCode;
        this.detail = detail;
        this.errors = Collections.emptyMap();
    }
    
    public AuthenticationResponse(int httpCode, Map<String, List<String>> errors){
        this.code = null;
        this.transactionId = null;
        this.username = null;
        this.replyMessage = null;
        this.httpCode = httpCode;
        this.detail = null;
        this.errors = errors;
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
        
        for (Entry<String, List<String>> errors : this.errors.entrySet()) {
            sb.append(errors.getKey()).append(" [");
            for (String error : errors.getValue()) {
                sb.append(error).append(", ");
            }
            sb.append("]").append(SEPARATOR);
        }
        
        return sb.toString();
    }

    public Map<String, List<String>> getErrors() {
        return errors;
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
