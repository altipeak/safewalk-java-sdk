package com.altipeak.safewalk;

import com.altipeak.safewalk.helper.ServerConnectivityHelper;
import com.altipeak.safewalk.helper.ServerConnectivityHelper.ConnectivityException;
import com.altipeak.safewalk.helper.ServerConnectivityHelperImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SafewalkClientImplTest extends TestCase
{
    private static final String HOST = "https://192.168.77.106";
    private static final long  PORT = 8443;
    private static final String AUTHENTICATION_API_ACCESS_TOKEN = "1c52926ef844c6b549a9a1b90436f78d0d7f3a3a";
    private static final String ADMIN_API_ACCESS_TOKEN = "59414d98a82ef3304abdd18e6580853b916e822f";
    private static final String INTERNAL_USERNAME = "internal";
    private static final String LDAP_USERNAME = "sw999408";
    private static final String FAST_AUTH_USERNAME = "fastauth";
    
    
    private ServerConnectivityHelper serverConnectivityHelper = new ServerConnectivityHelperImpl(HOST, PORT);
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SafewalkClientImplTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        System.setProperty("jsse.enableSNIExtension", "false");
        return new TestSuite( SafewalkClientImplTest.class );
    }

    public void testWithInternalUser() throws ConnectivityException {
        testSafewalkClient(INTERNAL_USERNAME);
    }
    
    public void testWithLdapUser() throws ConnectivityException {
        testSafewalkClient(LDAP_USERNAME);
    }
    
    private void testSafewalkClient(String username) throws ConnectivityException {
        System.out.println("\nBEGIN TEST");
        SafewalkClient client = new SafewalkClientImpl(this.serverConnectivityHelper);
        //
        AuthenticationResponse response1 = client.authenticate(AUTHENTICATION_API_ACCESS_TOKEN, username, "12345");
        System.out.println("STATIC PASSWORD AUTHENTICATION RESPONSE : " + response1);
        //
        SessionKeyResponse response10 = client.getSessionKeyChallenge(ADMIN_API_ACCESS_TOKEN);
        System.out.println("GET SESSION KEY RESPONSE : " + response10);
        //
        SessionKeyResponse response11 = client.verifySessionKeyChallenge(ADMIN_API_ACCESS_TOKEN, FAST_AUTH_USERNAME, response10.getChallenge());
        System.out.println("VERIFY SESSION KEY RESPONSE : " + response11);
        // 
        SignatureResponse response12 = client.sendPushSignature(ADMIN_API_ACCESS_TOKEN, FAST_AUTH_USERNAME,"abcde", "A160E4F805C51261541F0AD6BC618AE10BEB3A30786A099CE67DBEFD4F7F929F","All the data here will be signed. This request was generated from Safewalk API.","Sign Transaction","Push signature triggered from safewalk API");
        System.out.println("PUSH SIGNATURE RESPONSE OPTION 1: " + response12);
        //
        SignatureResponse response13 = client.sendPushSignature(ADMIN_API_ACCESS_TOKEN, FAST_AUTH_USERNAME,"abcde", "A160E4F805C51261541F0AD6BC618AE10BEB3A30786A099CE67DBEFD4F7F929F","All the data here will be signed. This request was generated from Safewalk API.","Sign Document","Push signature triggered from safewalk API");
        System.out.println("PUSH SIGNATURE RESPONSE OPTION 2 : " + response13);
        //
        SignatureResponse response14 = client.sendPushSignature(ADMIN_API_ACCESS_TOKEN, FAST_AUTH_USERNAME,"abcde", "A160E4F805C51261541F0AD6BC618AE10BEB3A30786A099CE67DBEFD4F7F929F","All the data here will be signed. This request was generated from Safewalk API.","Digital Signature Request","Push signature triggered from safewalk API");
        System.out.println("PUSH SIGNATURE RESPONSE OPTION 3: " + response14);
        //
        AuthenticationResponse response15 = client.authenticate(AUTHENTICATION_API_ACCESS_TOKEN, FAST_AUTH_USERNAME, "abcde");
        System.out.println("PUSH AUTHENTICATION RESPONSE : " + response15);
        //
        AuthenticationResponse response16 = client.authenticatePasswordExternal(AUTHENTICATION_API_ACCESS_TOKEN, username, "abcde");
        System.out.println("EXTERNAL AUTHENTICATION RESPONSE : " + response16);
        
    }
   
}
