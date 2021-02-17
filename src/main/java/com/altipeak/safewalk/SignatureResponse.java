package com.altipeak.safewalk;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

public class SignatureResponse {

    private final int httpCode;
    private final Map<String, List<String>> errors;
    private static final String SEPARATOR = " | ";
    private String signResult = "";
    private String rejectReason = "";
    private String allResponse = "";
    
    
    // ************************************
    // * Constructors
    // ************************************
    
    public SignatureResponse(int httpCode){
        this.httpCode = httpCode;
        this.errors = Collections.emptyMap();
        this.allResponse = "";
    }
    
    public SignatureResponse(int httpCode, String signResult, String rejectReason, String jsonResponse){
        this.httpCode = httpCode;
        this.signResult = signResult;
        this.rejectReason = rejectReason;
        this.errors = Collections.emptyMap();
        this.allResponse = jsonResponse;
    }
    
    public SignatureResponse(int httpCode, Map<String, List<String>> errors){
        this.httpCode = httpCode;
        this.errors = errors;
        this.allResponse = "";
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
    
    /**
     * @since v1.5
     * @return the complete response dictionary
     * different that what it is in {@link AuthenticationResponse#getAttributes()}
     */
    public Map<String, String> getAtributtes() {
        Map<String, String> atributes = null;
		try {
			JSONObject json = new JSONObject(allResponse);
			atributes = new HashMap<String, String>();
			Set<String> keys = json.keySet();
			for (String key : keys) {
			    atributes.put(key, json.getString(key));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return atributes;
    }
}
