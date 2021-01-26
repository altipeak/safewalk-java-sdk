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
   
    /* Authentication response */
    private static final String JSON_AUTH_REPLY_MESSAGE_FIELD = "reply-message";
    private static final String JSON_AUTH_REPLY_CODE_FIELD = "reply-code";
    private static final String JSON_AUTH_CODE_FIELD = "code";
    private static final String JSON_AUTH_TRANSACTION_FIELD = "transaction-id";
    private static final String JSON_AUTH_USERNAME_ID_FIELD = "username";
    private static final String JSON_AUTH_DETAIL_FIELD = "detail";
    private static final String JSON_AUTH_CHALLENGE_FIELD = "challenge";
    /* Create user response */
    private static final String JSON_CREATE_USER_USERNAME_FIELD = "username";
    private static final String JSON_CREATE_USER_FIRST_NAME_FIELD = "first_name";
    private static final String JSON_CREATE_USER_LAST_NAME_FIELD = "last_name";
    private static final String JSON_CREATE_USER_DN_FIELD = "dn";
    private static final String JSON_CREATE_USER_DB_MOBILE_PHONE_FIELD = "db_mobile_phone";
    private static final String JSON_CREATE_USER_DB_EMAIL_FIELD = "db_email";
    private static final String JSON_CREATE_USER_LDAP_MOBILE_PHONE_FIELD = "ldap_mobile_phone";
    private static final String JSON_CREATE_USER_LDAP_EMAIL_FIELD = "ldap_email";
    private static final String JSON_CREATE_USER_STORAGE_FIELD = "user_storage";
    /* Get user response */
    private static final String JSON_GET_USER_USERNAME_FIELD = "username";
    private static final String JSON_GET_USER_FIRST_NAME_FIELD = "first_name";
    private static final String JSON_GET_USER_LAST_NAME_FIELD = "last_name";
    private static final String JSON_GET_USER_DN_FIELD = "dn";
    private static final String JSON_GET_USER_DB_MOBILE_PHONE_FIELD = "db_mobile_phone";
    private static final String JSON_GET_USER_DB_EMAIL_FIELD = "db_email";
    private static final String JSON_GET_USER_LDAP_MOBILE_PHONE_FIELD = "ldap_mobile_phone";
    private static final String JSON_GET_USER_LDAP_EMAIL_FIELD = "ldap_email";
    private static final String JSON_GET_USER_STORAGE_FIELD = "user_storage";
    private static final String JSON_GET_USER_IS_LOCKED_FIELD = "is_locked";
    /* Associate token response */
    private static final String JSON_ASSOCIATE_TOKEN_FAIL_TO_SEND_REG_CODE_FIELD = "fail_to_send_registration_code";
    private static final String JSON_ASSOCIATE_TOKEN_FAIL_TO_SEND_DOWNLOAD_LINKS_FIELD = "fail_to_send_download_links";
    private static final String AUTHENTICATION_FOR_REGISTRATION_ACCESS_TOKEN = "access_token";
    /* Associations response */
    private static final String JSON_GET_TOKEN_ASSOCIATIONS_DEVICE_TYPE_FIELD = "type";
    private static final String JSON_GET_TOKEN_ASSOCIATIONS_SERIAL_NUMBER_FIELD = "serial_number";
    private static final String JSON_GET_TOKEN_ASSOCIATIONS_CONFIRMED_FIELD = "confirmed";
    private static final String JSON_GET_TOKEN_ASSOCIATIONS_PASSWORD_REQUIRED_FIELD = "password_required";
    /* Delete associations response */
    private static final String JSON_DELETE_TOKEN_ASSOCIATION_CODE_FIELD = "code";
    /* Create registration code response */
    private static final String JSON_CREATE_REGISTRATION_CODE_CODE_FIELD = "code";
    /* Registration response */
    private static final String JSON_REGISTRATION_SERIAL_NUMBER_FIELD = "serial_number";
    
    private final ServerConnectivityHelper serverConnetivityHelper;
    
    public SafewalkClientImpl(ServerConnectivityHelper serverConnetivityHelper) {
        this.serverConnetivityHelper = serverConnetivityHelper;
    }
    
    // ************************************
    // * Public Methods
    // ************************************
    
    @Override
    public AuthenticationResponse authenticate(final String accessToken, final String username, final String password) throws ConnectivityException {
        return this.authenticate(accessToken, username, password, "");
    }

    @Override
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
    public SignatureResponse sendPushSignature(final String accessToken, final String username, final String password) throws ConnectivityException {
    	Map<String, String> parameters = new HashMap<String, String>() { 
            private static final long serialVersionUID = 1L;
            {
                put("username", username);
                put("password", password);
                put("hash", "A160E4F805C51261541F0AD6BC618AE10BEB3A30786A099CE67DBEFD4F7F929F");
                put("data", "All the data here will be signed. This request was generated from Safewalk API.");
                put("title", "Sign transaction");
                put("body", "Push signature triggered from safewalk API");
            }
        };
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Response response = serverConnetivityHelper.post("/api/v1/auth/push_signature/", parameters, headers);
        if ( response.getResponseCode() == 200 ) {
            return new SignatureResponse(response.getResponseCode());
        }else if ( response.getResponseCode() == 400 ){
            return new SignatureResponse(response.getResponseCode(), getErrors(response.getContent()));
        }else{
        	return new SignatureResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
	}
    
    @Override
    public CreateUserResponse createUser(final String accessToken, final String username, final String password, final String firstName, final String lastName, final String mobilePhone, final String email, final String parent) throws ConnectivityException {
        Map<String, String> parameters = new HashMap<String, String>() { 
            private static final long serialVersionUID = 1L;
            {
                put("username", username);
                put("password", password);
                put("first_name", firstName);
                put("last_name", lastName);
                put("mobile_phone", mobilePhone);
                put("email", email);
                put("parent", parent);
            }
        };
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Response response = serverConnetivityHelper.post("/api/v1/admin/user/?format=json", parameters, headers);
        if ( response.getResponseCode() == 201 ) {
            JSONObject jsonResponse = new JSONObject(response.getContent());
            return new CreateUserResponse(response.getResponseCode()
                    , this.getString(jsonResponse, JSON_CREATE_USER_USERNAME_FIELD)
                    , this.getString(jsonResponse, JSON_CREATE_USER_FIRST_NAME_FIELD)
                    , this.getString(jsonResponse, JSON_CREATE_USER_LAST_NAME_FIELD)
                    , this.getString(jsonResponse, JSON_CREATE_USER_DN_FIELD)
                    , this.getString(jsonResponse, JSON_CREATE_USER_DB_MOBILE_PHONE_FIELD) 
                    , this.getString(jsonResponse, JSON_CREATE_USER_DB_EMAIL_FIELD)
                    , this.getString(jsonResponse, JSON_CREATE_USER_LDAP_MOBILE_PHONE_FIELD) 
                    , this.getString(jsonResponse, JSON_CREATE_USER_LDAP_EMAIL_FIELD)
                    , (this.getString(jsonResponse, JSON_CREATE_USER_STORAGE_FIELD) != null) ? CreateUserResponse.UserStorage.valueOf(jsonResponse.getString(JSON_GET_USER_STORAGE_FIELD)) : null);
        }else{
            return new CreateUserResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
    
    @Override
    public UpdateUserResponse updateUser(final String accessToken, final String username, final String mobilePhone, final String email) throws ConnectivityException {
        Map<String, String> parameters = new HashMap<String, String>() { 
            private static final long serialVersionUID = 1L;
            {
                put("mobile_phone", mobilePhone);
                put("email", email);
            }
        };
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Response response = serverConnetivityHelper.put(String.format("/api/v1/admin/user/%s/?format=json", username), parameters, headers);
        if ( response.getResponseCode() == 200 ) {
            return new UpdateUserResponse(response.getResponseCode());
        }else{
            return new UpdateUserResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
    
    @Override
    public GetUserResponse getUser(final String accessToken, final String username) throws ConnectivityException {
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Map<String, String> parameters = new HashMap<String, String>() { 
            private static final long serialVersionUID = 1L;
            {
                put("format", "json");
            }
        };
        Response response = serverConnetivityHelper.get(String.format("/api/v1/admin/user/%s/", username), parameters, headers);
        if ( response.getResponseCode() == 200 ) {
            JSONObject jsonResponse = new JSONObject(response.getContent());
            return new GetUserResponse(response.getResponseCode()
                    , this.getString(jsonResponse, JSON_GET_USER_USERNAME_FIELD)
                    , this.getString(jsonResponse, JSON_GET_USER_FIRST_NAME_FIELD)
                    , this.getString(jsonResponse, JSON_GET_USER_LAST_NAME_FIELD)
                    , this.getString(jsonResponse, JSON_GET_USER_DN_FIELD)
                    , this.getString(jsonResponse, JSON_GET_USER_DB_MOBILE_PHONE_FIELD) 
                    , this.getString(jsonResponse, JSON_GET_USER_DB_EMAIL_FIELD)
                    , this.getString(jsonResponse, JSON_GET_USER_LDAP_MOBILE_PHONE_FIELD) 
                    , this.getString(jsonResponse, JSON_GET_USER_LDAP_EMAIL_FIELD)
                    , (this.getString(jsonResponse, JSON_GET_USER_STORAGE_FIELD) != null) ? GetUserResponse.UserStorage.valueOf(jsonResponse.getString(JSON_GET_USER_STORAGE_FIELD)) : null
                    , this.getBoolean(jsonResponse, JSON_GET_USER_IS_LOCKED_FIELD));
        }else{
            return new GetUserResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
    
    @Override
    public DeleteUserResponse deleteUser(String accessToken, String username) throws ConnectivityException {
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Response response = serverConnetivityHelper.delete(String.format("/api/v1/admin/user/%s/", username), parameters, headers);
        if ( response.getResponseCode() == 204 ) {
            return new DeleteUserResponse(response.getResponseCode());
        }else{
            return new DeleteUserResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
    
    @Override
    public SetStaticPasswordResponse setStaticPassword(final String accessToken, final String username, final String password) throws ConnectivityException {
        Map<String, String> parameters = new HashMap<String, String>() { 
            private static final long serialVersionUID = 1L;
            {
                put("username", username);
                put("password", password);
            }
        };
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Response response = serverConnetivityHelper.post("/api/v1/admin/staticpassword/set/", parameters, headers);
        if ( response.getResponseCode() == 200 ) {
            return new SetStaticPasswordResponse(response.getResponseCode());
        }else{
            return new SetStaticPasswordResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
    
    @Override
    public AssociateTokenResponse associateToken(final String accessToken, final String username, final DeviceType deviceType) throws ConnectivityException {
        return associateToken(accessToken, username, deviceType, null, null);
    }
    
    @Override
    public AssociateTokenResponse associateToken(final String accessToken, final String username, final DeviceType deviceType, final Boolean sendRegistrationCode, final Boolean sendDownloadLinks) throws ConnectivityException {
        Map<String, String> parameters = new HashMap<String, String>() { 
            private static final long serialVersionUID = 1L;
            {
                put("username", username);
                put("device_type", deviceType.getCode());
            }
        };
        if ( sendDownloadLinks != null ) {
            parameters.put("send_download_links", String.valueOf(sendDownloadLinks));
        }
        if ( sendRegistrationCode != null ) {
            parameters.put("send_registration_code", String.valueOf(sendRegistrationCode));
        }
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Response response = serverConnetivityHelper.post(String.format("/api/v1/admin/user/%s/devices/?format=json", username), parameters, headers);
        if ( response.getResponseCode() == 200 ) {
            JSONObject jsonResponse = new JSONObject(response.getContent());
            return new AssociateTokenResponse(response.getResponseCode()
                                            , this.getBoolean(jsonResponse, JSON_ASSOCIATE_TOKEN_FAIL_TO_SEND_REG_CODE_FIELD)
                                            , this.getBoolean(jsonResponse, JSON_ASSOCIATE_TOKEN_FAIL_TO_SEND_DOWNLOAD_LINKS_FIELD));
        }else{
            return new AssociateTokenResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
    
    @Override
    public GetTokenAssociationsResponse getTokenAssociations(String accessToken, String username) throws ConnectivityException {
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Map<String, String> parameters = new HashMap<String, String>() { 
            private static final long serialVersionUID = 1L;
            {
                put("format", "json");
            }
        };
        Response response = serverConnetivityHelper.get(String.format("/api/v1/admin/user/%s/devices/", username), parameters, headers);
        if ( response.getResponseCode() == 200 ) {
            JSONArray jsonResponse = new JSONArray(response.getContent());
            List<TokenAssociation> associations = new ArrayList<TokenAssociation>();
            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject json = jsonResponse.getJSONObject(i);
                associations.add(new TokenAssociation(
                          this.getString(json, JSON_GET_TOKEN_ASSOCIATIONS_DEVICE_TYPE_FIELD)
                        , this.getString(json, JSON_GET_TOKEN_ASSOCIATIONS_SERIAL_NUMBER_FIELD)
                        , this.getBoolean(json, JSON_GET_TOKEN_ASSOCIATIONS_CONFIRMED_FIELD)
                        , this.getBoolean(json, JSON_GET_TOKEN_ASSOCIATIONS_PASSWORD_REQUIRED_FIELD)));
            }
            return new GetTokenAssociationsResponse(response.getResponseCode(), associations);
            
        }else{
            return new GetTokenAssociationsResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
    
    @Override
    public DeleteTokenAssociation deleteTokenAssociation(String accessToken, String username, DeviceType deviceType, String serialNumber) throws ConnectivityException {
        Map<String, String> parameters = new HashMap<String, String>(){
            private static final long serialVersionUID = 1L;
            {
                put("format", "json");
            }
        };
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Response response = serverConnetivityHelper.delete(String.format("/api/v1/admin/user/%s/devices/%s/%s/", username, deviceType.getCode(), serialNumber), parameters, headers);
        if ( response.getResponseCode() == 200 || response.getResponseCode() == 400) {
            JSONObject jsonResponse = new JSONObject(response.getContent());
            return new DeleteTokenAssociation(response.getResponseCode(), this.getString(jsonResponse, JSON_DELETE_TOKEN_ASSOCIATION_CODE_FIELD));
        }else {
            return new DeleteTokenAssociation(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
    
    @Override
    public CreateRegistrationCode createRegistrationCode(String accessToken,final String username) throws ConnectivityException {
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Response response = serverConnetivityHelper.post(String.format("/api/v1/admin/user/%s/registrationtoken/?format=json", username), parameters, headers);
        if ( response.getResponseCode() == 200 ) {
            return new CreateRegistrationCode(response.getResponseCode());
        }else if ( response.getResponseCode() == 400 ){
            JSONObject jsonResponse = new JSONObject(response.getContent());
            return new CreateRegistrationCode(response.getResponseCode(), getErrors(response.getContent()), this.getString(jsonResponse, JSON_CREATE_REGISTRATION_CODE_CODE_FIELD));
        }else{
            return new CreateRegistrationCode(response.getResponseCode(), getErrors(response.getContent()), null);
        }
    }
    
    @Override
    public SessionKeyResponse getQr(String accessToken) throws ConnectivityException {
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Response response = serverConnetivityHelper.post("/api/v1/auth/session_key/?format=json", parameters, headers);
        if ( response.getResponseCode() == 200 ) {
        	JSONObject jsonResponse = new JSONObject(response.getContent());
            return new SessionKeyResponse(response.getResponseCode(), this.getString(jsonResponse, JSON_AUTH_CHALLENGE_FIELD));
        }else if ( response.getResponseCode() == 400 ){
            JSONObject jsonResponse = new JSONObject(response.getContent());
            return new SessionKeyResponse(response.getResponseCode(), getErrors(response.getContent()));
        }else{
            return new SessionKeyResponse(response.getResponseCode(), getErrors(response.getContent()));
        }
    }
    
    @Override
    public  SessionKeyResponse verifyQrStatus(String accessToken, final String username, String sessionKey) throws ConnectivityException {
        Map<String, String> parameters = new HashMap<String, String>(){
        private static final long serialVersionUID = 1L;
        {
            put("username", username);
           
        }
    	};
        Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
        Response response = serverConnetivityHelper.get("/api/v1/auth/session_key/"+sessionKey.trim()+"/", parameters, headers);
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
	public AuthenticationResponse authenticatePasswordExternal(String accessToken, final String username, final String password) throws ConnectivityException {
	Map<String, String> parameters = new HashMap<String, String>() { 
        private static final long serialVersionUID = 1L;
        {
            put("username", username);
            put("password", password);
        }
    };
    Map<String, String> headers = Collections.singletonMap("Authorization", "Bearer " + accessToken);
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
