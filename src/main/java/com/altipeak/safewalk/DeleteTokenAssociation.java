package com.altipeak.safewalk;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DeleteTokenAssociation {

    private final int httpCode;
    
    private final Map<String, List<String>> errors;
    
    private final String code;
    
    private static final String SEPARATOR = " | ";
    
    // ************************************
    // * Constructors
    // ************************************
    
    /*package*/ DeleteTokenAssociation(int httpCode, String code){
        this.code = code;
        this.httpCode = httpCode;
        this.errors = Collections.emptyMap();
    }
    
    /*package*/ DeleteTokenAssociation(int httpCode, Map<String, List<String>> errors){
        this.code = null;
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
        sb.append(String.valueOf(this.code)).append(SEPARATOR);
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
