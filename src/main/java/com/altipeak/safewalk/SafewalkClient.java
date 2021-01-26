package com.altipeak.safewalk;

import com.altipeak.safewalk.helper.ServerConnectivityHelper.ConnectivityException;

/**
 * Safewalk integration Client
 * 
 * @since v1.1.10
 */
public interface SafewalkClient {
    
    /**
     * <p>
     * Authenticates the user with the given credentials.
     * </p>
     * <p>
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     * 
     * @since v1.1.10
     * @param accessToken
     * @param username
     * @param password
     * @return {@link AuthenticationResponse}
     * @throws ConnectivityException
     */
    AuthenticationResponse authenticate(String accessToken, String username, String password) throws ConnectivityException;
    
    /**
     * <p>
     * Authenticates the user with the given credentials.
     * </p>
     * <p>
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     * 
     * @since v1.1.10
     * @param accessToken
     * @param username
     * @param password
     * @param transactionId
     * @return {@link AuthenticationResponse}
     * @throws ConnectivityException
     */
    AuthenticationResponse authenticate(String accessToken, String username, String password, String transactionId) throws ConnectivityException;
    
    /**
     * <p>
     * Creates a user with the given {@code username}, {@code password}, {@code firstName}, {@code lastName}, {@code mobilePhone} and {@code email}.
     * </p>
     * <p>
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database).
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     * 
     * @since v1.5
     * @param accessToken
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param mobilePhone
     * @param email
     * @param parent
     * @return {@link CreateUserResponse}
     * @throws ConnectivityException
     */
    CreateUserResponse createUser(String accessToken, String username, String password, String firstName, String lastName, String mobilePhone, String email, String parent) throws ConnectivityException;
 
    /**
     * <p>
     * Updates the mobile phone number and the email of the user with the given {@code username}. 
     * </p>
     * <p>
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     * 
     * @since v1.1.10
     * @param accessToken
     * @param username
     * @param mobilePhone
     * @param email
     * @return {@link UpdateUserResponse}
     * @throws ConnectivityException
     */
    UpdateUserResponse updateUser(String accessToken, String username, String mobilePhone, String email) throws ConnectivityException;
    
    /**
     * <p>
     * Returns the information of the user with the given {@code username}. 
     * </p>
     * 
     * @since v1.1.10
     * @param accessToken
     * @param username
     * @return {@link GetUserResponse}
     * @throws ConnectivityException
     */
    GetUserResponse getUser(String accessToken, String username) throws ConnectivityException;
    
    /**
     * <p>
     * Deletes the user with the given {@code username}.
     * </p>
     * <p>
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     * 
     * @since v1.5
     * @param accessToken
     * @param username
     * @return {@link DeleteUserResponse}
     * @throws ConnectivityException
     */
    DeleteUserResponse deleteUser(String accessToken, String username) throws ConnectivityException;
    
    /**
     * Sets the static password of the user with the given {@code username}
     * <p>
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     * 
     * @since v1.5
     * @param accessToken
     * @param username
     * @param password
     * @return {@link SetStaticPasswordResponse}
     * @throws ConnectivityException
     */
    SetStaticPasswordResponse setStaticPassword(String accessToken, String username, String password) throws ConnectivityException;
    
    /**
     * Associates a device to the user with the given {@code username}
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     *  
     * @since v1.1.10
     * @param accessToken
     * @param username
     * @param deviceType
     * @return {@link AssociateTokenResponse}
     * @throws ConnectivityException
     */
    AssociateTokenResponse associateToken(String accessToken, String username, DeviceType deviceType) throws ConnectivityException;
    
    /**
     * Associates a device to the user with the given {@code username}. If {@code sendRegistrationCode} or {@code sendDownloadLinks} are true, it will also send the indicated information to the user.
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     * 
     * @since v1.1.10
     * @param accessToken
     * @param username
     * @param deviceType
     * @param sendRegistrationCode
     * @param sendDownloadLinks
     * @return {@link AssociateTokenResponse}
     * @throws ConnectivityException
     */
    AssociateTokenResponse associateToken(String accessToken, String username, DeviceType deviceType, Boolean sendRegistrationCode, Boolean sendDownloadLinks) throws ConnectivityException;
    
    /**
     * Returns the associated devices for the user with the given {@code username}
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p> 
     * 
     * @since v1.1.10
     * @param accessToken
     * @param username
     * @return {@link GetTokenAssociationsResponse}
     * @throws ConnectivityException
     */
    GetTokenAssociationsResponse getTokenAssociations(String accessToken, String username) throws ConnectivityException;
    
    /**
     * Removes a specific device from the user with the given {@code username}
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p> 
     * 
     * @since v1.1.10
     * @param accessToken
     * @param username
     * @param deviceType
     * @param serialNumber
     * @return {@link DeleteTokenAssociation}
     * @throws ConnectivityException
     */
    DeleteTokenAssociation deleteTokenAssociation(String accessToken, String username, DeviceType deviceType, String serialNumber) throws ConnectivityException;
    
    /**
     * Sends a registration code to the user with the given {@code username}
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p> 
     * 
     * @since v1.1.10
     * @deprecated since v1.5
     * @param accessToken
     * @param username
     * @return {@link CreateRegistrationCode}
     * @throws ConnectivityException
     */
    CreateRegistrationCode createRegistrationCode(String accessToken, String username) throws ConnectivityException;
    
    /**
     * <p>
     * Get's the sessionKey string to sign with QR. 
     * </p>
     * <p>
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     * 
     * @since v1.1.10
     * @param accessToken
     * @return {@link SessionKeyResponse}
     * @throws ConnectivityException
     */
    SessionKeyResponse getQr(String accessToken) throws ConnectivityException;
    
    /**
     * <p>
     *  Verifies the status of QR sessionKey. 
     * </p>
     * <p>
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     * 
     * @since v1.1.10
     * @param accessToken
     * @param username
     * @param password
     * @return {@link SessionKeyResponse}
     * @throws ConnectivityException
     */
    SessionKeyResponse verifyQrStatus(String accessToken, String username, String password) throws ConnectivityException;
    
    /**
     * <p>
     *  Sends a Push signature to the mobile device.
     * </p>
     * <p>
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     * 
     * @since v1.1.10
     * @param accessToken
     * @param username
     * @param password
     * @return {@link SignatureResponse}
     * @throws ConnectivityException
     */
    SignatureResponse sendPushSignature(String accessToken,final String username, final String password) throws ConnectivityException;
    
    /**
     * <p>
     *  Standard authentication for external users.
     * </p>
     * <p>
     * The username will determine if the user is an internal user or an LDAP user. 
     * <ul>
     *   <li>If the given username has the format of &lt;username&gt; the user will be created as an internal user (In the database). If it doesn't exist, it will look in the LDAP following the priority order.
     *   <li>If the given username has the format of &lt;username&gt@&lt;domain&gt; the user will be created in the LDAP with the given domain.
     * </ul>
     * </p>
     * 
     * @since v1.1.10
     * @param accessToken
     * @param username
     * @param password
     * @return {@link AuthenticationResponse}
     * @throws ConnectivityException
     */
    AuthenticationResponse authenticatePasswordExternal(String accessToken,final String username, final String password) throws ConnectivityException;
}
