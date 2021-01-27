package com.altipeak.safewalk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.altipeak.safewalk.AuthenticationResponse.AuthenticationCode;
import com.altipeak.safewalk.AuthenticationResponse.ReplyCode;
import com.altipeak.safewalk.GetTokenAssociationsResponse.TokenAssociation;
import com.altipeak.safewalk.helper.ServerConnectivityHelper;
import com.altipeak.safewalk.helper.ServerConnectivityHelper.ConnectivityException;
import com.altipeak.safewalk.helper.ServerConnectivityHelper.Response;

public class SafewalkClientImpl implements SafewalkClient
{
	private static String adminAccessToken = "";
	private static String authAccessToken = "";
		
	/* Authentication response */
    private static final String JSON_AUTH_REPLY_MESSAGE_FIELD = "reply-message";
    private static final String JSON_AUTH_REPLY_CODE_FIELD = "reply-code";
    private static final String JSON_AUTH_CODE_FIELD = "code";
    private static final String JSON_AUTH_TRANSACTION_FIELD = "transaction-id";
    private static final String JSON_AUTH_USERNAME_ID_FIELD = "username";
    private static final String JSON_AUTH_DETAIL_FIELD = "detail";
    private static final String JSON_AUTH_CHALLENGE_FIELD = "challenge";
    private static final String JSON_SIGN_RESULT_FIELD = "result";
    private static final String JSON_SIGN_REJECT_REASON_FIELD = "reason";
    
    
    private final ServerConnectivityHelper serverConnetivityHelper;
    
    public SafewalkClientImpl(ServerConnectivityHelper serverConnetivityHelper, String adminAccessToken, String authAccessToken) {
        this.serverConnetivityHelper = serverConnetivityHelper;
        SafewalkClientImpl.adminAccessToken = adminAccessToken;
        SafewalkClientImpl.authAccessToken = authAccessToken;
    }
  
    // ************************************
    // * Public Methods
    // ************************************
    
    @Override
    public AuthenticationResponse authenticate(final String username, final String password) throws ConnectivityException {
        return this.authenticate(username, password, "");
    }

    @Override
    public AuthenticationResponse authenticate(final String username, final String password, final String transactionId) throws ConnectivityException{
        Map<String, String> parameters = new HashMap<String, String>() { 
            private static final long serialVersionUID = 1L;
            {
                put("username", username);
                put("password", password);
                put("transaction_id", transactionId);
            }
        };
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + authAccessToken);
        Response response = serverConnetivityHelper.post("/api/v1/auth/authenticate/?format=json", parameters, headers);
        if ( response.getResponseCode() == 200 || response.getResponseCode() == 401 ) {
            
            JSONObject jsonResponse = new JSONObject(response.getContent());
            
            return new AuthenticationResponse(
                     response.getResponseCode()
                   , this.getAuthenticationCode(jsonResponse, JSON_AUTH_CODE_FIELD)
                   , this.getString(jsonResponse, JSON_AUTH_TRANSACTION_FIELD)
                   , this.getString(jsonResponse, JSON_AUTH_USERNAME_ID_FIELD)
                   , this.getString(jsonResponse, JSON_AUTH_REPLY_MESSAGE_FIELD)
                   , this.getReplyCode(jsonResponse, JSON_AUTH_REPLY_CODE_FIELD)
                   , this.getString(jsonResponse, JSON_AUTH_DETAIL_FIELD)
            );
        }else{
            return new AuthenticationResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
    
    @Override
    public SignatureResponse sendPushSignature(final String username, final String password, final String hash, final String data, final String title, final String body) throws ConnectivityException {
    	Map<String, String> parameters = new HashMap<String, String>() { 
            private static final long serialVersionUID = 1L;
            {
                put("username", username);
                put("password", password);
                put("hash",  hash);
                if ( data != null )   put("data",  data);
                if ( title != null )  put("title", title);
                if ( body  != null )  put("body",  body);
            }
        };
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + adminAccessToken);
        Response response = serverConnetivityHelper.post("/api/v1/auth/push_signature/?format=json", parameters, headers);
        if ( response.getResponseCode() == 200 ) {
        	JSONObject jsonResponse = new JSONObject(response.getContent());
            return new SignatureResponse(response.getResponseCode(), this.getString(jsonResponse, JSON_SIGN_RESULT_FIELD), this.getString(jsonResponse, JSON_SIGN_REJECT_REASON_FIELD));
        }else if ( response.getResponseCode() == 400 ){
            return new SignatureResponse(response.getResponseCode(), getErrors(response.getContent()));
        }else{
        	return new SignatureResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
	}
    
    @Override
    public SessionKeyResponse createSessionKeyChallenge() throws ConnectivityException {
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + adminAccessToken);
        Response response = serverConnetivityHelper.post("/api/v1/auth/session_key/?format=json", parameters, headers);
        if ( response.getResponseCode() == 200 ) {
        	JSONObject jsonResponse = new JSONObject(response.getContent());
            return new SessionKeyResponse(response.getResponseCode(), this.getString(jsonResponse, JSON_AUTH_CHALLENGE_FIELD));
        }else if ( response.getResponseCode() == 400 ){
            return new SessionKeyResponse(response.getResponseCode(), getErrors(response.getContent()));
        }else{
            return new SessionKeyResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
    
    @Override
    public  SessionKeyResponse verifySessionKeyStatus(final String sessionKeyChallenge) throws ConnectivityException {
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + adminAccessToken);
        Response response = serverConnetivityHelper.get(String.format("/api/v1/auth/session_key/%s/",sessionKeyChallenge), parameters, headers);
        if ( response.getResponseCode() == 200 ) {
        	JSONObject jsonResponse = new JSONObject(response.getContent());
        	return new SessionKeyResponse(response.getResponseCode(), this.getString(jsonResponse, JSON_AUTH_CODE_FIELD));
        }else if ( response.getResponseCode() == 400 ){
            return new SessionKeyResponse(response.getResponseCode(), getErrors(response.getContent()));
        }else{
        	return new SessionKeyResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
        
    @Override
	public AuthenticationResponse authenticateExternal(final String username) throws ConnectivityException {
	Map<String, String> parameters = new HashMap<String, String>() { 
        private static final long serialVersionUID = 1L;
        {
            put("username", username);
        }
    };
    Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + adminAccessToken);
    Response response = serverConnetivityHelper.post("/api/v1/auth/pswdcheckedauth/?format=json", parameters, headers);
    if ( response.getResponseCode() == 200 || response.getResponseCode() == 401 ) {
        
        JSONObject jsonResponse = new JSONObject(response.getContent());
        
        return new AuthenticationResponse(
                 response.getResponseCode()
               , this.getAuthenticationCode(jsonResponse, JSON_AUTH_CODE_FIELD)
               , this.getString(jsonResponse, JSON_AUTH_TRANSACTION_FIELD)
               , this.getString(jsonResponse, JSON_AUTH_USERNAME_ID_FIELD)
               , this.getString(jsonResponse, JSON_AUTH_REPLY_MESSAGE_FIELD)
               , this.getReplyCode(jsonResponse, JSON_AUTH_REPLY_CODE_FIELD)
               , this.getString(jsonResponse, JSON_AUTH_DETAIL_FIELD)
        );
    }else{
        return new AuthenticationResponse(response.getResponseCode(), getErrors(response.getContent()));
    }
	}
    
    
    // ************************************
    // * Private Methods
    // ************************************
    
    private String getString(JSONObject json, String key) {
        return (json.has(key) && !json.isNull(key)) ? json.getString(key) : null;
    } 
    
    private Boolean getBoolean(JSONObject json, String key) {
        return (json.has(key) && !json.isNull(key)) ? json.getBoolean(key) : null;
    } 
    
    private AuthenticationCode getAuthenticationCode(JSONObject json, String key){
        return (this.getString(json, key) != null) ? AuthenticationCode.valueOf(json.getString(key)) : null;
    }
    
    private ReplyCode getReplyCode(JSONObject json, String key){
        return (this.getString(json, key) != null) ? ReplyCode.valueOf(json.getString(key)) : null;
    }
              
    private Map<String, List<String>> getErrors(String errors){
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        try {
            JSONObject jsonResponse = new JSONObject(errors);  
            Iterator<?> keys = jsonResponse.keys();
            while( keys.hasNext() ) {
                String key = (String) keys.next();
                List<String> values = new ArrayList<String>();
                try {
                    JSONArray jsonValues = jsonResponse.getJSONArray(key);
                    for (int i = 0; i < jsonValues.length(); i++) {
                        values.add(String.valueOf(jsonValues.get(i)));
                    }
                }catch (JSONException e) {
                   values.add(String.valueOf(jsonResponse.get(key)));
                }
                result.put(key, values);
            }
        }catch (JSONException e){
            return Collections.emptyMap();
        }
        return result;
    }
}
