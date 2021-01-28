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
     * @param username
     * @param password - Static password or OTP
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
     * @param username
     * @param password - Static password or OTP
     * @param transactionId - It can be used to link the authentication transaction with a previous authentication transaction.
     * @return {@link AuthenticationResponse}
     * @throws ConnectivityException
     */
    AuthenticationResponse authenticate(String username, String password, String transactionId) throws ConnectivityException;
    
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
     * @param sessionKeyChallenge - The challenge obtained with createSessionKeyChallenge()
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
     * @param username
     * @param password
     * @param hash   
     * @param data  -  The data to sign. Data or body are required.
     * @param title -  The title displayed in the mobile device. Optional.
     * @param body  -  The body of the message. Data or body are required.
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
     * @param username
     * @return {@link AuthenticationResponse}
     * @throws ConnectivityException
     */
    AuthenticationResponse authenticateExternal(final String username) throws ConnectivityException;
}
