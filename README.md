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

SafewalkClient client = new SafewalkClientImpl(this.serverConnectivityHelper);
CreateUserResponse response2 = client.createUser(ADMIN_API_ACCESS_TOKEN, "testuser@local.com", "12345", "Test", "User", "+5491222546985", "test@email.com", "");
//
AuthenticationResponse response1 = client.authenticate(AUTHENTICATION_API_ACCESS_TOKEN, "testuser@local.com", "12345");
//
UpdateUserResponse response3 = client.updateUser(ADMIN_API_ACCESS_TOKEN, "testuser@local.com", "+549122254116985", "test111@email.com");
//
GetUserResponse response4 = client.getUser(ADMIN_API_ACCESS_TOKEN, "testuser@local.com");
//
SetStaticPasswordResponse response5 = client.setStaticPassword(ADMIN_API_ACCESS_TOKEN, "testuser@local.com", "abcde");
//
AssociateTokenResponse response6 = client.associateToken(ADMIN_API_ACCESS_TOKEN, "testuser@local.com", DeviceType.SESAMI_MOBILE, false, false);
//
GetTokenAssociationsResponse response7 = client.getTokenAssociations(ADMIN_API_ACCESS_TOKEN, "testuser@local.com");
//
DeleteTokenAssociation response8 = client.deleteTokenAssociation(ADMIN_API_ACCESS_TOKEN, "testuser@local.com", DeviceType.getEnum(response7.getAssociations().get(0).getDeviceType()), response7.getAssociations().get(0).getSerialNumber());
//
DeleteUserResponse response9 = client.deleteUser(ADMIN_API_ACCESS_TOKEN, "testuser@local.com");
```
* host : The server host
* port : The server port
* AUTHENTICATION_API_ACCESS_TOKEN : The access token of the system user created to access the authentication-api
* ADMIN_API_ACCESS_TOKEN : The access token of the system user created to access the admin-api 

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
