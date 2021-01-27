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
    
    private String code = "";
    
    // ************************************
    // * Constructors
    // ************************************
    
    public SignatureResponse(int httpCode){
        this.httpCode = httpCode;
        this.errors = Collections.emptyMap();
    }
    
    public SignatureResponse(int httpCode, String code){
        this.httpCode = httpCode;
        this.code = code;
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
        if ( this.code != null ) sb.append(this.code).append(SEPARATOR);     
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
        return code;
    }
}
