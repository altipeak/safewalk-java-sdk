package com.altipeak.safewalk;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Authentication Response
 * 
 * @since v1.1.10
 */
public class AuthenticationResponse {

    private final AuthenticationCode code;
    private final String transactionId;
    private final String username;
    private final String replyMessage;
    private final String allResponse;
    private final ReplyCode replyCode;
    
    private final int httpCode;
    private final String detail;
    
    private final Map<String, List<String>> errors;
    
    private static final String SEPARATOR = " | ";

    /**
     * @since 1.1.10
     */
    public enum AuthenticationCode {
        ACCESS_ALLOWED,
        ACCESS_CHALLENGE,
        ACCESS_DENIED;
    }
    
    
    /**
     * @since 1.1.10
     */
    public enum ReplyCode {
        /**
         * <p>
         * The user is locked. {@link AuthenticationResponse#getReplyMessage()} returns <code>"The user is locked, please contact your system administrator"</code>
         * </p>
         * <p>
         *   Only used when {@link AuthenticationResponse#getCode()} returns {@link AuthenticationCode#ACCESS_DENIED}
         * </p>
         */
        USR_LOCKED,
        /**
         * <p>
         *   An internal error occured. {@link AuthenticationResponse#getReplyMessage()} returns <code>"Internal system error, please contact your system administrator."</code>Causes
         *   for this are:
         *   <ul>
         *     <li> LDAP Connection error
         *     <li> Invalid license
         *     <li> The system fails to send the otp (only for Hybrid and Virtual devices)
         *   </ul>
         * </p> 
         * <p>
         *   Only used when {@link AuthenticationResponse#getCode()} returns {@link AuthenticationCode#ACCESS_DENIED}
         * </p>
         */
        INTERNAL_SYSTEM_ERROR,
        
        /**
         * <p>
         *   Invalid credentials. {@link AuthenticationResponse#getReplyMessage()} returns <code>"Invalid credentials, please make sure you entered your username and up to date password/otp correctly"</code>
         * </p>
         * <p>
         *   Only used when {@link AuthenticationResponse#getCode()} returns {@link AuthenticationCode#ACCESS_DENIED}
         * </p>
         */
        INVALID_CREDENTIALS,
        /** 
         * <p>
         * OTP required. {@link AuthenticationResponse#getReplyMessage()} returns <code>"Please enter your OTP code"</code>. 
         * </p>
         * <p>
         * Only used when {@link AuthenticationResponse#getCode()} returns {@link AuthenticationCode#ACCESS_CHALLENGE}
         * </p>
         * <p>
         * This is the case where the user has a token with password required enabled (2 Step authentication).
         * </p> 
         */
        OTP_REQUIRED,
        /** 
         * <p>
         * The device (token) is out of sync. {@link AuthenticationResponse#getReplyMessage()} returns <code>"Please enter an additional OTP code to synchronize your account"</code>. 
         * </p>
         * <p>
         * Only used when {@link AuthenticationResponse#getCode()} returns {@link AuthenticationCode#ACCESS_CHALLENGE}
         * </p>
         */
        DEVICE_OUT_OF_SYNC
    }
    
    /*protected*/ AuthenticationResponse(int httpCode
                                , AuthenticationCode code
                                , String transactionId
                                , String username
                                , String replyMessage
                                , ReplyCode replyCode
                                , String detail
                                , String jsonResponse) {
        this.code = code;
        this.transactionId = transactionId;
        this.username = username;
        this.replyMessage = replyMessage;
        this.replyCode = replyCode;
        this.httpCode = httpCode;
        this.detail = detail;
        this.allResponse = jsonResponse;
        this.errors = Collections.emptyMap();
    }
    
    /*protected*/ AuthenticationResponse(int httpCode, Map<String, List<String>> errors){
        this.code = null;
        this.transactionId = null;
        this.username = null;
        this.replyMessage = null;
        this.replyCode = null;
        this.httpCode = httpCode;
        this.detail = null;
        this.allResponse = null;
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
        if ( this.transactionId != null ) sb.append(this.transactionId).append(SEPARATOR);
        if ( this.username != null ) sb.append(this.username).append(SEPARATOR);
        if ( this.replyMessage != null ) sb.append(this.replyMessage).append(SEPARATOR);
        if ( this.replyCode != null ) sb.append(this.replyCode).append(SEPARATOR);
        if ( this.detail != null ) sb.append(this.detail).append(SEPARATOR);
             
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
     * Useful when {@link AuthenticationResponse#getHttpCode()} is 400
     * 
     * @since v1.1.10
     * @return a {@link Map} with the fields that contains errors. Example : 
     * <pre>
     * {
     *   "password": [
     *       "This field is required."
     *   ]
     * }
     * </pre>
     * 
     */
    public Map<String, List<String>> getErrors() {
        return errors;
    }

    /**
     * @since v1.1.10
     * @return the authentication result 
     */
    public AuthenticationCode getCode() {
        return code;
    }

    /**
     * @since v1.1.10
     * @return the transactionId generated by the server (or passed by parameter). Useful to link transactions between the client and Safewalk.
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @since v1.1.10
     * @return the authenticated username if {@link AuthenticationResponse#getCode()} is {@link AuthenticationCode#ACCESS_ALLOWED} or {@link AuthenticationCode#ACCESS_CHALLENGE} otherwise is null
     */
    public String getUsername() {
        return username;
    }

    /**
     * @since v1.1.10
     * @return the replay message from the server (Only available in English) if {@link AuthenticationResponse#getHttpCode()} is 401, 200 or 500 (Can't connect to LDAP) otherwise is null
     */
    public String getReplyMessage() {
        return replyMessage;
    }

    /**
     * @since v1.1.10
     * @return 
     * <ul>
     *   <li> 200 if {@link AuthenticationCode#ACCESS_ALLOWED}
     *   <li> 401 if {@link AuthenticationCode#ACCESS_CHALLENGE} or {@link AuthenticationCode#ACCESS_DENIED}. Also if the OAUTH2 access token is not valid. 
     *   <li> 500 if can't contact LDAP
     * </ul>
     */
    public int getHttpCode() {
        return httpCode;
    }

    
    /**
     * @since v1.1.10
     * @return A detail where it explains why the OAUTH2 access token is not valid. Only available if {@link AuthenticationResponse#getHttpCode()} is 401 and {@link AuthenticationResponse#getCode()} is null.
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @since v1.5
     * @return the replay code from the server if {@link AuthenticationResponse#getHttpCode()} is 401, 200 or 500 (Can't connect to LDAP) otherwise is null. This can be used to present a message
     * different that what it is in {@link AuthenticationResponse#getReplyMessage()}
     */
    public ReplyCode getReplyCode() {
        return replyCode;
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
