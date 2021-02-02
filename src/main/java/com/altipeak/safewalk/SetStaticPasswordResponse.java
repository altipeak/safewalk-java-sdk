package com.altipeak.safewalk;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SetStaticPasswordResponse {

    private final int httpCode;
    
    private final Map<String, List<String>> errors;
    
    private static final String SEPARATOR = " | ";
    
    // ************************************
    // * Constructors
    // ************************************
    
    /*package*/ SetStaticPasswordResponse(int httpCode){
        this.httpCode = httpCode;
        this.errors = Collections.emptyMap();
    }
    
    /*package*/ SetStaticPasswordResponse(int httpCode, Map<String, List<String>> errors){
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
        
}
