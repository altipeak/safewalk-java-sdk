package com.altipeak.safewalk;

import com.altipeak.safewalk.helper.ServerConnectivityHelper;
import com.altipeak.safewalk.helper.ServerConnectivityHelper.ConnectivityException;
import com.altipeak.safewalk.helper.ServerConnectivityHelperImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SafewalkClientImplTest extends TestCase
{
    private static final String  HOST = "https://192.168.0.39";
    private static final long    PORT = 8445;
    private static final boolean BYPASS_SSL_CHECK = true;
    private static final String  AUTHENTICATION_API_ACCESS_TOKEN = "6ea8b958e7afc1022c22ebd8b9776797fe3f3cdb";
    private String staticPasswordUserName = "internal";
    private String fastAuthUserName = "internal2";
    
    
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
        testSafewalkClient(staticPasswordUserName, fastAuthUserName);
    }
  
    
    private void testSafewalkClient(String staticPasswordUserName, String fastAuthUserName) throws ConnectivityException {
        System.out.println("\nBEGIN TEST");
        SafewalkClient client = new SafewalkClientImpl(this.serverConnectivityHelper, null, AUTHENTICATION_API_ACCESS_TOKEN);
        //
        AuthenticationResponse response1 = client.authenticate(staticPasswordUserName, "12345");
        System.out.println("USER CREDENTIALS AUTHENTICATION RESPONSE : " + response1 + " METHOD " + response1.getAtributtes().get("auth-method"));
        //
        SessionKeyResponse response10 = client.createSessionKeyChallenge();
        System.out.println("GET SESSION KEY RESPONSE : " + response10);
        //
        SessionKeyResponse response11 = client.verifySessionKeyStatus(response10.getChallenge());
        System.out.println("VERIFY SESSION KEY RESPONSE : " + response11);
        //
        SignatureResponse response12 = client.sendPushSignature(fastAuthUserName,"abcde", "A160E4F805C51261541F0AD6BC618AE10BEB3A30786A099CE67DBEFD4F7F929F","All the data here will be signed. This request was generated from Safewalk API.","Sign Transaction","Push signature triggered from safewalk API");
        System.out.println("PUSH SIGNATURE RESPONSE OPTION 1: " + response12);
        //
        SignatureResponse response13 = client.sendPushSignature(fastAuthUserName,"abcde", "25A0DCC3DD1D78EF2D2FC5E6F606A0DB0ECD8B427A0417D8C94CC51139CF4FC8","This call includes the data", null, null);
        System.out.println("PUSH SIGNATURE RESPONSE OPTION 2: " + response13);
        //
        SignatureResponse response14 = client.sendPushSignature(fastAuthUserName,"abcde", "25A0DCC3DD1D78EF2D2FC5E6F606A0DB0ECD8B427A0417D8C94CC51139CF4FC8",null, null, "This call includes the body");
        System.out.println("PUSH SIGNATURE RESPONSE OPTION 3: " + response14);
        //
        AuthenticationResponse response15 = client.authenticate(fastAuthUserName, "abcde");
        System.out.println("PUSH AUTHENTICATION RESPONSE : " + response15);
        //
        AuthenticationResponse response16 = client.authenticateExternal(staticPasswordUserName);
        System.out.println("EXTERNAL AUTHENTICATION RESPONSE : " + response16 );
        
    }
   
}
