package com.altipeak.safewalk;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AssociateTokenResponse {

    private final int httpCode;
    
    private final Map<String, List<String>> errors;
    
    private final Boolean failToSendRegistrationCode;
    private final Boolean failToSendDownloadLinks;
    
    private static final String SEPARATOR = " | ";
    
    // ************************************
    // * Constructors
    // ************************************
    
    public AssociateTokenResponse(int httpCode
                                , Boolean failToSendRegistrationCode
                                , Boolean failToSendDownloadLinks){
        this.failToSendDownloadLinks = failToSendDownloadLinks;
        this.failToSendRegistrationCode = failToSendRegistrationCode;
        this.httpCode = httpCode;
        this.errors = Collections.emptyMap();
    }
    
    public AssociateTokenResponse(int httpCode, Map<String, List<String>> errors){
        this.failToSendDownloadLinks = null;
        this.failToSendRegistrationCode = null;
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
            sb.append(this.failToSendRegistrationCode).append(SEPARATOR);
            sb.append(this.failToSendDownloadLinks).append(SEPARATOR);
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
     * @return the failToSendRegistrationCode
     */
    public Boolean getFailToSendRegistrationCode() {
        return failToSendRegistrationCode;
    }

    /**
     * @return the failToSendDownloadLinks
     */
    public Boolean getFailToSendDownloadLinks() {
        return failToSendDownloadLinks;
    }    
    
    
}
