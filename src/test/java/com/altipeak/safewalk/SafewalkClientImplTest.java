package com.altipeak.safewalk;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.altipeak.safewalk.helper.ServerConnectivityHelper;
import com.altipeak.safewalk.helper.ServerConnectivityHelper.ConnectivityException;
import com.altipeak.safewalk.helper.ServerConnectivityHelperImpl;

public class SafewalkClientImplTest  extends TestCase
{
    private static final String HOST = "https://192.168.1.160";
    private static final long  PORT = 8443;
    private static final String ACCESS_TOKEN = "1453ce44c4ac3e7a6576e717d903aa8109bd2a8a";
    
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
        return new TestSuite( SafewalkClientImplTest.class );
    }

    public void testAccessAllowed() throws ConnectivityException {
        SafewalkClient client = new SafewalkClientImpl(this.serverConnectivityHelper);
        AuthenticationResponse response = client.authenticate(ACCESS_TOKEN, "admin", "admin");
        System.out.println("\n\n\n######################################  AUTHENTICATION RESPONSE ######################################");
        System.out.println(response);
        System.out.println("######################################################################################################\n\n\n");
    }
}
