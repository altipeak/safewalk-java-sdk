# JAVA SDK module for Safewalk integration

* [Authentication API](#authentication-api)

<a name="authentication-api"></a>
## Authentication API

### Usage
```java
String host = "https://192.168.1.160";
long  port = 8443;
private static final String AUTHENTICATION_API_ACCESS_TOKEN = "c4608fc697e844829bb5a27cce13737250161bd0";
private static final String ADMIN_API_ACCESS_TOKEN = "1237d30e0f29e6e59bb5a27cce1373722c72c749";
private static final String INTERNAL_USERNAME = "internal";
private static final boolean BYPASS_SSL_CHECK = false;
private static final String  STATIC_PASSWORD_USERNAME = "internal";
private static final String  FAST_AUTH_USERNAME = "fastauth";

        System.out.println("\nBEGIN TEST");
        SafewalkClient client = new SafewalkClientImpl(this.serverConnectivityHelper, ADMIN_API_ACCESS_TOKEN, AUTHENTICATION_API_ACCESS_TOKEN);
        //
        AuthenticationResponse response1 = client.authenticate(username, "12345");
        System.out.println("STATIC PASSWORD AUTHENTICATION RESPONSE : " + response1);
        //
        SessionKeyResponse response10 = client.createSessionKeyChallenge();
        System.out.println("GET SESSION KEY RESPONSE : " + response10);
        //
        SessionKeyResponse response11 = client.verifySessionKeyStatus(response10.getChallenge());
        System.out.println("VERIFY SESSION KEY RESPONSE : " + response11);
        //
        SignatureResponse response12 = client.sendPushSignature(FAST_AUTH_USERNAME,"abcde", "A160E4F805C51261541F0AD6BC618AE10BEB3A30786A099CE67DBEFD4F7F929F","All the data           here will be signed. This request was generated from Safewalk API.","Sign Transaction","Push signature triggered from safewalk API");
        System.out.println("PUSH SIGNATURE RESPONSE OPTION 1: " + response12);
        //
        SignatureResponse response13 = client.sendPushSignature(FAST_AUTH_USERNAME,"abcde", "25A0DCC3DD1D78EF2D2FC5E6F606A0DB0ECD8B427A0417D8C94CC51139CF4FC8","This call             includes the data", null, null);
        System.out.println("PUSH SIGNATURE RESPONSE OPTION 2 : " + response13);
        //
        SignatureResponse response14 = client.sendPushSignature(FAST_AUTH_USERNAME,"abcde", "25A0DCC3DD1D78EF2D2FC5E6F606A0DB0ECD8B427A0417D8C94CC51139CF4FC8",null, null,             "This call includes the body");
        System.out.println("PUSH SIGNATURE RESPONSE OPTION 3: " + response14);
        //
        AuthenticationResponse response15 = client.authenticate(FAST_AUTH_USERNAME, "abcde");
        System.out.println("PUSH AUTHENTICATION RESPONSE : " + response15);
        //
        AuthenticationResponse response16 = client.authenticateExternal(username);
        System.out.println("EXTERNAL AUTHENTICATION RESPONSE : " + response16);

```
* host : The server host.
* port : The server port.
* AUTHENTICATION_API_ACCESS_TOKEN : The access token of the system user created to access the authentication-api.
* ADMIN_API_ACCESS_TOKEN : The access token of the system user created to access the admin-api. 
* STATIC_PASSWORD_USERNAME : An LDAP or internal user with no licenses asigned and password authentication allowed. 
* FAST_AUTH_USER : The user registered in safewalk with a Fast:Auth:Sign license.
* BYPASS_SSL_CHECK : To allow untrusted certificates.
### Authentication Response Examples (AuthenticationResponse class)

The response below show the result of providing valid credentials
```
200 | ACCESS_ALLOWED | admin
```

The response below show the result when the access token is not valid (to fix it, check for the access token in the superadmin console)
```
401 | Invalid token
```

The response below show the result when no access token is provided (to fix it, check for the access token in the superadmin console)
```
401 | Invalid bearer header. No credentials provided.
```

The response below show the result when the credentials (username / password) are not valid
```
401 | ACCESS_DENIED | Invalid credentials, please make sure you entered your username and up to date password/otp correctly
```

The response below show the result when the user is locked
```
401 | ACCESS_DENIED | The user is locked, please contact your system administrator
```

The response below show the result when the user is required to enter an OTP
```
401 | ACCESS_CHALLENGE | admin | Please enter your OTP code
```
