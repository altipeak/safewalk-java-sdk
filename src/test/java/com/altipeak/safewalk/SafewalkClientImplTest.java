package com.altipeak.safewalk;

import com.altipeak.safewalk.helper.ServerConnectivityHelper;
import com.altipeak.safewalk.helper.ServerConnectivityHelper.ConnectivityException;
import com.altipeak.safewalk.helper.ServerConnectivityHelperImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SafewalkClientImplTest  extends TestCase
{
    private static final String HOST = "https://192.168.1.160";
    private static final long  PORT = 8443;
    private static final String AUTHENTICATION_API_ACCESS_TOKEN = "c4608fc697e844829bb5a27cce13737250161bd0";
    private static final String ADMIN_API_ACCESS_TOKEN = "1237d30e0f29e6e59bb5a27cce1373722c72c749";
    
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

    public void testSafewalkClient() throws ConnectivityException {
        SafewalkClient client = new SafewalkClientImpl(this.serverConnectivityHelper);
        AuthenticationResponse response1 = client.authenticate(AUTHENTICATION_API_ACCESS_TOKEN, "admin", "admin");
        System.out.println("AUTHENTICATION RESPONSE : " + response1);
        //
        CreateUserResponse response2 = client.createUser(ADMIN_API_ACCESS_TOKEN, "testuser", "12345", "Test", "User", "+5491222546985", "test@email.com", "");
        System.out.println("CREATE USER RESPONSE : " + response2);
        //
        UpdateUserResponse response3 = client.updateUser(ADMIN_API_ACCESS_TOKEN, "testuser", "+549122254116985", "test111@email.com");
        System.out.println("UPDATE USER RESPONSE : " + response3);
        //
        GetUserResponse response4 = client.getUser(ADMIN_API_ACCESS_TOKEN, "testuser");
        System.out.println("GET USER RESPONSE : " + response4);
        //
        SetStaticPasswordResponse response5 = client.setStaticPassword(ADMIN_API_ACCESS_TOKEN, "testuser", "abcde");
        System.out.println("SET STATIC PASSWORD RESPONSE : " + response5);
        //
        AssociateTokenResponse response6 = client.associateToken(ADMIN_API_ACCESS_TOKEN, "testuser", DeviceType.SESAMI_MOBILE, false, false);
        System.out.println("ASSOCIATE TOKEN RESPONSE : " + response6);
        //
        GetTokenAssociationsResponse response7 = client.getTokenAssociations(ADMIN_API_ACCESS_TOKEN, "testuser");
        System.out.println("GET ASSOCIATIONS RESPONSE : " + response7);
        //
        DeleteTokenAssociation response8 = client.deleteTokenAssociation(ADMIN_API_ACCESS_TOKEN, "testuser", DeviceType.getEnum(response7.getAssociations().get(0).getDeviceType()), response7.getAssociations().get(0).getSerialNumber());
        System.out.println("DELETE ASSOCIATIONS RESPONSE : " + response8);
        //
        DeleteUserResponse response9 = client.deleteUser(ADMIN_API_ACCESS_TOKEN, "testuser");
        System.out.println("DELETE USER RESPONSE : " + response9);
    }
   
}
