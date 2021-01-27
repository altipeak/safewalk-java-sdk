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
private static final String FAST_AUTH_USERNAME = "fastauth";

 SafewalkClient client = new SafewalkClientImpl(this.serverConnectivityHelper);
 AuthenticationResponse response1 = client.authenticate(AUTHENTICATION_API_ACCESS_TOKEN, username, "12345");
 System.out.println("STATIC PASSWORD AUTHENTICATION RESPONSE : " + response1);
 //
 SessionKeyResponse response10 = client.getSessionKeyChallenge(ADMIN_API_ACCESS_TOKEN);
 System.out.println("GET SESSION KEY RESPONSE : " + response10);
 //
 SessionKeyResponse response11 = client.verifySessionKeyChallenge(ADMIN_API_ACCESS_TOKEN, FAST_AUTH_USERNAME, response10.getChallenge());
 System.out.println("VERIFY SESSION KEY RESPONSE : " + response11);
 // 
 SignatureResponse response12 = client.sendPushSignature(ADMIN_API_ACCESS_TOKEN, FAST_AUTH_USERNAME,"abcde");
 System.out.println("PUSH SIGNATURE RESPONSE : " + response12);
 //
 AuthenticationResponse response13 = client.authenticate(AUTHENTICATION_API_ACCESS_TOKEN, FAST_AUTH_USERNAME, "abcde");
 System.out.println("PUSH AUTHENTICATION RESPONSE : " + response13);
 //
 AuthenticationResponse response14 = client.authenticatePasswordExternal(AUTHENTICATION_API_ACCESS_TOKEN, username, "abcde");
 System.out.println("EXTERNAL AUTHENTICATION RESPONSE : " + response14);
```
* host : The server host
* port : The server port
* AUTHENTICATION_API_ACCESS_TOKEN : The access token of the system user created to access the authentication-api
* ADMIN_API_ACCESS_TOKEN : The access token of the system user created to access the admin-api 
* FAST_AUTH_USER : The user registered in safewalk with a Fast:Auth:Sign license. 

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
