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
    AuthenticationResponse authenticate(String username, String password) throws ConnectivityException;
    
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
     * @param password static password or OTP
     * @param transactionId it can be used to link the authentication transaction with a previous authentication transaction.
     * @return {@link AuthenticationResponse}
     * @throws ConnectivityException
     */
    AuthenticationResponse authenticate(String username, String password, String transactionId) throws ConnectivityException;
    
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
    SessionKeyResponse createSessionKeyChallenge() throws ConnectivityException;
    
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
    SessionKeyResponse verifySessionKeyStatus(String sessionKeyChallenge) throws ConnectivityException;
    
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
     * @return {@link SignatureResponse}
     * @throws ConnectivityException
     */
    SignatureResponse sendPushSignature(final String username, final String password, final String hash, final String data, final String title, final String body) throws ConnectivityException;
    
    /**
     * <p>
     *   Standard authentication for external users.
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
     * @return {@link AuthenticationResponse}
     * @throws ConnectivityException
     */
    AuthenticationResponse authenticatePasswordExternal(final String username) throws ConnectivityException;
}
