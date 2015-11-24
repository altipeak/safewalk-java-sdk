package com.altipeak.safewalk;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CreateRegistrationCode {

    private final int httpCode;
    
    private final Map<String, List<String>> errors;
    private final String code;
    
    private static final String SEPARATOR = " | ";
    
    
    // ************************************
    // * Constructors
    // ************************************
    
    /*package*/ CreateRegistrationCode(int httpCode){
        
        this.httpCode = httpCode;
        this.errors = Collections.emptyMap();
        this.code = null;
        
    }
    
    /*package*/ CreateRegistrationCode(int httpCode, Map<String, List<String>> errors, String code){
        this.httpCode = httpCode;
        this.errors = errors;
        this.code = code;
    }
    
    // ************************************
    // * Public Methods
    // ************************************

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(this.httpCode)).append(SEPARATOR);
        if ( this.httpCode != 200 ) {
            if ( this.code != null ) {
                sb.append(this.code).append(SEPARATOR);
            }
            for (Entry<String, List<String>> errors : this.errors.entrySet()) {
                sb.append(errors.getKey()).append(" [");
                for (String error : errors.getValue()) {
                    sb.append(error).append(", ");
                }
                sb.append("]").append(SEPARATOR);
            }
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
     * @return the httpCode
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    
        
}
