package com.altipeak.safewalk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.altipeak.safewalk.AuthenticationResponse.AuthenticationCode;
import com.altipeak.safewalk.helper.ServerConnectivityHelper;
import com.altipeak.safewalk.helper.ServerConnectivityHelper.ConnectivityException;
import com.altipeak.safewalk.helper.ServerConnectivityHelper.Response;

/**
 * Safewalk integration Client
 */

public class SafewalkClientImpl implements SafewalkClient
{
    
    private static final String JSON_REPLY_MESSAGE_FIELD = "reply-message";
    private static final String JSON_CODE_FIELD = "code";
    private static final String JSON_TRANSACTION_ID_FIELD = "transaction_id";
    private static final String JSON_USERNAME_ID_FIELD = "username";
    private static final String JSON_DETAIL_FIELD = "detail";
    
    private final ServerConnectivityHelper serverConnetivityHelper;
    
    public SafewalkClientImpl(ServerConnectivityHelper serverConnetivityHelper) {
        this.serverConnetivityHelper = serverConnetivityHelper;
    }
    
    // ************************************
    // * Public Methods
    // ************************************
    
    public AuthenticationResponse authenticate(final String accessToken, final String username, final String password) throws ConnectivityException {
        return this.authenticate(accessToken, username, password, "");
    }

    public AuthenticationResponse authenticate(final String accessToken, final String username, final String password, final String transactionId) throws ConnectivityException{
        Map<String, String> parameters = new HashMap<String, String>() { 
            private static final long serialVersionUID = 1L;
            {
                put("username", username);
                put("password", password);
                put("transaction_id", transactionId);
            }
        };
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Response response = serverConnetivityHelper.post("/api/v1/auth/authenticate/?format=json", parameters, headers);
        JSONObject jsonResponse = new JSONObject(response.getContent());
        return new AuthenticationResponse(
                this.getAuthenticationCode(jsonResponse, JSON_CODE_FIELD)
               ,this.getString(jsonResponse, JSON_TRANSACTION_ID_FIELD)
               ,this.getString(jsonResponse, JSON_USERNAME_ID_FIELD)
               ,this.getString(jsonResponse, JSON_REPLY_MESSAGE_FIELD)
               ,response.getResponseCode()
               ,this.getString(jsonResponse, JSON_DETAIL_FIELD)
        );
    }
    
    // ************************************
    // * Private Methods
    // ************************************
    
    private String getString(JSONObject json, String key) {
        return (json.has(key)) ? json.getString(key) : null;
    } 
    
    private AuthenticationCode getAuthenticationCode(JSONObject json, String key){
        return (this.getString(json, key) != null) ? AuthenticationCode.valueOf(json.getString(key)) : null;
    }
    
}
