package com.altipeak.safewalk;

import com.altipeak.safewalk.helper.ServerConnectivityHelper;
import com.altipeak.safewalk.helper.ServerConnectivityHelper.ConnectivityException;
import com.altipeak.safewalk.helper.ServerConnectivityHelperImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SafewalkClientImplTest  extends TestCase
{
    private static final String HOST = "https://192.168.1.150";
    private static final long  PORT = 8443;
    private static final String AUTHENTICATION_API_ACCESS_TOKEN = "92c1c93e61cc1ad13d80f9d345e025f1a14587b9";
    private static final String ADMIN_API_ACCESS_TOKEN = "8d875fade285624f16430b68cbd45156b63a5c1d";
    private static final String INTERNAL_USERNAME = "sgiinternaluser";
    private static final String LDAP_USERNAME = "sgildapuser@local.com";
    
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
        CreateUserResponse response2 = client.createUser(ADMIN_API_ACCESS_TOKEN, username, "12345", "Test", "User", "+5491222546985", "s12313@gmail.com", "");
        System.out.println("CREATE USER RESPONSE : " + response2);
        //
        AuthenticationResponse response1 = client.authenticate(AUTHENTICATION_API_ACCESS_TOKEN, username, "12345");
        System.out.println("AUTHENTICATION RESPONSE : " + response1);
        //
        UpdateUserResponse response3 = client.updateUser(ADMIN_API_ACCESS_TOKEN, username, "+549122254116985", "s12313@gmail.com");
        System.out.println("UPDATE USER RESPONSE : " + response3);
        //
        GetUserResponse response4 = client.getUser(ADMIN_API_ACCESS_TOKEN, username);
        System.out.println("GET USER RESPONSE : " + response4);
        //
        SetStaticPasswordResponse response5 = client.setStaticPassword(ADMIN_API_ACCESS_TOKEN, username, "abcde");
        System.out.println("SET STATIC PASSWORD RESPONSE : " + response5);
        //
        AssociateTokenResponse response6 = client.associateToken(ADMIN_API_ACCESS_TOKEN, username, DeviceType.SESAMI_MOBILE, false, false);
        System.out.println("ASSOCIATE TOKEN RESPONSE : " + response6);
        //
        CreateRegistrationCode response7 = client.createRegistrationCode(ADMIN_API_ACCESS_TOKEN, username);
        System.out.println("CREATE REGISTRATION TOKEN RESPONSE : " + response7);
        //
        GetTokenAssociationsResponse response8 = client.getTokenAssociations(ADMIN_API_ACCESS_TOKEN, username);
        System.out.println("GET ASSOCIATIONS RESPONSE : " + response8);
        //
        if ( response8.getAssociations().size() > 0 ) {
          DeleteTokenAssociation response9 = client.deleteTokenAssociation(ADMIN_API_ACCESS_TOKEN, username, DeviceType.getEnum(response8.getAssociations().get(0).getDeviceType()), response8.getAssociations().get(0).getSerialNumber());
          System.out.println("DELETE ASSOCIATIONS RESPONSE : " + response9);
        }
        //
        DeleteUserResponse response10 = client.deleteUser(ADMIN_API_ACCESS_TOKEN, username);
        System.out.println("DELETE USER RESPONSE : " + response10);
    }
   
}
