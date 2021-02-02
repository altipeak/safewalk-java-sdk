package com.altipeak.safewalk;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ChallengeResponse {

    private final int httpCode;
    
    private final Map<String, List<String>> errors;
    
    private String challenge = " ";
    
    private static final String SEPARATOR = " | ";
    
    // ************************************
    // * Constructors
    // ************************************
    public ChallengeResponse(int httpCode){
        this.httpCode = httpCode;
        this.errors = Collections.emptyMap();
    }
    
    public ChallengeResponse(int httpCode, String challenge){
        this.httpCode = httpCode;
        this.challenge = challenge;
        this.errors = Collections.emptyMap();
    }
    
    public ChallengeResponse(int httpCode, String challenge, String serialNumber){
        this.httpCode = httpCode;
        this.challenge = challenge;
        this.errors = Collections.emptyMap();
    }
    
    public ChallengeResponse(int httpCode, Map<String, List<String>> errors){
        this.challenge = null;
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
        if ( this.httpCode == 200 ) {
            sb.append(this.challenge).append(SEPARATOR);
        }
        
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
     * @return the challenge
     */
    public String getChallenge() {
        return challenge;
    }
         
}
