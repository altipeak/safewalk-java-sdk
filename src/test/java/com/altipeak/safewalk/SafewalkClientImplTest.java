package com.altipeak.safewalk;

import com.altipeak.safewalk.helper.ServerConnectivityHelper;
import com.altipeak.safewalk.helper.ServerConnectivityHelper.ConnectivityException;
import com.altipeak.safewalk.helper.ServerConnectivityHelperImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SafewalkClientImplTest extends TestCase
{
<<<<<<< HEAD
    private static final String  HOST = "https://192.168.1.171";
    private static final long    PORT = 8445;
    private static final boolean BYPASS_SSL_CHECK = true;
    private static final String  AUTHENTICATION_API_ACCESS_TOKEN = "d19930e00e1eed07764c88de56df1b364924fa65";
=======
    private static final String  HOST = "https://192.168.11.109";
    private static final long    PORT = 8445;
    private static final boolean BYPASS_SSL_CHECK = true;
    private static final String  AUTHENTICATION_API_ACCESS_TOKEN = "290fd37eeb01676c379e5de74b3d4e287e04f606";
>>>>>>> c6376d94bc698c309617e565df87181f9d1c860a
    private String userName = "internal";
    private String mobileUserName = "internal2";
    
    
    private ServerConnectivityHelper serverConnectivityHelper = new ServerConnectivityHelperImpl(HOST, PORT, BYPASS_SSL_CHECK);
    
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

    public void testAuthenticationMethods() throws ConnectivityException {
    	 System.out.println("\nBEGIN TEST");
         SafewalkClient client = new SafewalkClientImpl(this.serverConnectivityHelper, null, AUTHENTICATION_API_ACCESS_TOKEN);
         testUserCredentialsAuthenticationMethod(client);
         testQRAuthenticationMethod(client);
         testPushAuthenticationMethod(client);
         testPushSignatureAuthenticationMethod(client);
         testSecondStepAuthenticationMethod(client);
    }
    
    /**
     * <p>
     *  On this example a user without licenses is recommended to test one step / static password authentication.
     * </p>
     */
    
    private void testUserCredentialsAuthenticationMethod(SafewalkClient client) throws ConnectivityException {
        AuthenticationResponse response = client.authenticate(userName, "12345");
        System.out.println("USER CREDENTIALS AUTHENTICATION RESPONSE : " + response + " METHOD " + response.getAtributtes().get("auth-method"));

    }
    
    /**
     * <p>
     *  On this example first a sessionKey string is generated and then it's status is verified. When the sessionKey is generated, it can be copied and used with a third party QR code generator like https://es.qr-code-generator.com/ to be scanned and signed.
     * </p>
     * 
     */
    private void testQRAuthenticationMethod(SafewalkClient client) throws ConnectivityException {
    	// Here the sessionKey string is created. After it is printed in the console, it can be copied and pasted to https://es.qr-code-generator.com/, then a QR code will be generated to be signed with Fast Auth App. 
        SessionKeyResponse response1 = client.createSessionKeyChallenge();
        System.out.println("GET SESSION KEY RESPONSE : " + response1);
        // After the QR is signed, the status will be ACCESS_ALLOWED. While the QR is not signed status will be ACCESS_PENDING. 
        SessionKeyResponse response2 = client.verifySessionKeyStatus(response1.getChallenge());
        System.out.println("VERIFY SESSION KEY RESPONSE : " + response2);
    }
    
    /**
     * <p>
     *    On this example the same authenticate API as in UserCredentialsAuthenticationMethod is called, but with a user with a Fast:Auth license registered.
     * </p>
     */
    private void testPushAuthenticationMethod(SafewalkClient client) throws ConnectivityException {
    	 AuthenticationResponse response = client.authenticate(mobileUserName, "abcde");
         System.out.println("PUSH AUTHENTICATION RESPONSE : " + response);
    }
    
    /**
     * <p>
     *  On this example there are three calls to the push signature API with different parameter combinations.
     * </p>
     */
    private void testPushSignatureAuthenticationMethod(SafewalkClient client) throws ConnectivityException {
    	 // On this example all parameters are sent
    	 SignatureResponse response1 = client.sendPushSignature(mobileUserName,"abcde", "A160E4F805C51261541F0AD6BC618AE10BEB3A30786A099CE67DBEFD4F7F929F","All the data here will be signed. This request was generated from Safewalk API.","Sign Transaction","Push signature triggered from safewalk API");
         System.out.println("PUSH SIGNATURE RESPONSE OPTION 1: " + response1);
         // On this example body parameter is empty 
         SignatureResponse response2 = client.sendPushSignature(mobileUserName,"abcde", "25A0DCC3DD1D78EF2D2FC5E6F606A0DB0ECD8B427A0417D8C94CC51139CF4FC8","This call includes the data", "Sign Document", null);
         System.out.println("PUSH SIGNATURE RESPONSE OPTION 2: " + response2);
         // On this example data and title parameters are empty 
         SignatureResponse response3 = client.sendPushSignature(mobileUserName,"abcde", "25A0DCC3DD1D78EF2D2FC5E6F606A0DB0ECD8B427A0417D8C94CC51139CF4FC8",null, null, "This call includes the body");
         System.out.println("PUSH SIGNATURE RESPONSE OPTION 3: " + response3);
    }
    
    /**
     * <p>
     * On this example, Safewalk is called as a 2nd step authentication, as identity was first validated with an external system.
     * </p>
     */
    private void testSecondStepAuthenticationMethod(SafewalkClient client) throws ConnectivityException {
    	//
    	AuthenticationResponse response = client.secondStepAuthentication(userName);
        System.out.println("2ND STEP AUTHENTICATION RESPONSE : " + response );
    }
  
    
    
}
