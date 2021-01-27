package com.altipeak.safewalk;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.altipeak.safewalk.AuthenticationResponse.ReplyCode;

public class SignatureResponse {

    private final int httpCode;
    
    private final Map<String, List<String>> errors;
    
    private static final String SEPARATOR = " | ";
    
    private String signResult = "";
    
    private String rejectReason = "";
    
    // ************************************
    // * Constructors
    // ************************************
    
    public SignatureResponse(int httpCode){
        this.httpCode = httpCode;
        this.errors = Collections.emptyMap();
    }
    
    public SignatureResponse(int httpCode, String signResult, String rejectReason){
        this.httpCode = httpCode;
        this.signResult = signResult;
        this.rejectReason = rejectReason;
        this.errors = Collections.emptyMap();
    }
    
    public SignatureResponse(int httpCode, Map<String, List<String>> errors){
        this.httpCode = httpCode;
        this.errors = errors;
    }

    // ************************************
    // * Public Methods
    // ************************************

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(this.httpCode)).append(SEPARATOR);
        if ( this.signResult != null ) sb.append(this.signResult).append(SEPARATOR);     
        if ( this.rejectReason != null ) sb.append(this.rejectReason).append(SEPARATOR);     
        
        for (Entry<String, List<String>> errors : this.errors.entrySet()) {
            sb.append(errors.getKey()).append(" [");
            for (String error : errors.getValue()) {
                sb.append(error).append(", ");
            }
            sb.append("]").append(SEPARATOR); 
        }
        
        return sb.toString();
    }
    
    
    /**
     * @return the errors
     */
    public Map<String, List<String>> getErrors() {
        return errors;
    }
    
    /**
     * @return the code
     */
    public String getCode() {
        return signResult;
    }
}
