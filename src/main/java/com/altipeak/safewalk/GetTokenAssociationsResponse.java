package com.altipeak.safewalk;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GetTokenAssociationsResponse {

    private final int httpCode;
    
    private final Map<String, List<String>> errors;
    
    private final List<TokenAssociation> associations;
    
    private static final String SEPARATOR = " | ";
    
    // ************************************
    // * Constructors
    // ************************************
    
    /*package*/ GetTokenAssociationsResponse(int httpCode
                                , List<TokenAssociation> associations){
        this.associations = associations;
        this.httpCode = httpCode;
        this.errors = Collections.emptyMap();
    }
    
    /*package*/ GetTokenAssociationsResponse(int httpCode, Map<String, List<String>> errors){
        this.associations = Collections.emptyList();
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
            if ( this.associations.size() > 0 ) {
                sb.append("[");
                for (TokenAssociation tokenAssociation : associations) {
                    sb.append("(");
                    sb.append(tokenAssociation);
                    sb.append("), ");
                }
                sb.append("]");
            }else{
                sb.append("NO ASSOCIATIONS");
            }
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
     * @return the associations
     */
    public List<TokenAssociation> getAssociations() {
        return associations;
    }



    public static class TokenAssociation {
        
        private final String serialNumber ;
        private final String deviceType;
        private final Boolean confirmed;
        private final Boolean passwordRequired;
                
        public TokenAssociation(String deviceType
                              , String serialNumber
                              , Boolean confirmed
                              , Boolean passwordRequired){
            this.serialNumber = serialNumber;
            this.deviceType = deviceType;
            this.confirmed = confirmed;
            this.passwordRequired = passwordRequired;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.deviceType).append(", ");
            sb.append(this.serialNumber).append(", ");
            sb.append(this.confirmed);
            return sb.toString();
        }
        
        /**
         * @return the serialNumber
         */
        public String getSerialNumber() {
            return serialNumber;
        }

        /**
         * @return the deviceType
         */
        public String getDeviceType() {
            return deviceType;
        }

        /**
         * @return the confirmed
         */
        public Boolean getConfirmed() {
            return confirmed;
        }

        /**
         * @return the passwordRequired
         */
        public Boolean getPasswordRequired() {
            return passwordRequired;
        }       
        
        
    }
    
}
