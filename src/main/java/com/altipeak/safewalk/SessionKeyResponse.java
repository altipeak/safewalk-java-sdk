package com.altipeak.safewalk;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SessionKeyResponse {

    private final int httpCode;
    private final Map<String, List<String>> errors;
    private String code = " ";
    private static final String SEPARATOR = " | ";
    private final String allResponse;
    
    // ************************************
    // * Constructors
    // ************************************
    public SessionKeyResponse(int httpCode){
        this.httpCode = httpCode;
        this.errors = Collections.emptyMap();
        this.allResponse = "";
    }
    
    public SessionKeyResponse(int httpCode, String code, String jsonContent){
        this.httpCode = httpCode;
        this.code = code;
        this.errors = Collections.emptyMap();
        this.allResponse = jsonContent;
            
    }
    
    public SessionKeyResponse(int httpCode, Map<String, List<String>> errors){
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
        if ( this.httpCode == 200 ) {
            sb.append(this.code).append(SEPARATOR);
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
     * @return the code
     */
    public String getChallenge() {
        return code;
    }
    
    /**
     * @since v1.5
     * @return the complete response dictionary
     * different that what it is in {@link AuthenticationResponse#getAttributes()}
     */
    public Map<String, String> getAtributtes() {
        Map<String, String> atributes = null;
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(allResponse);
			atributes = new HashMap<String, String>();
			Set<Map.Entry<String, String>> entries = json.entrySet();
			for (Map.Entry<String, String> entry : entries) {
			    atributes.put(entry.getKey(), entry.getValue());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return atributes;
    }
}
