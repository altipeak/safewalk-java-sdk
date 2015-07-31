# JAVA SDK module for Safewalk integration

* [Authentication API](#authentication-api)

<a name="authentication-api"></a>
## Authentication API

### Usage
```java
String host = "https://192.168.1.160";
long  port = 8443;
String accessToken = "1453ce44c4ac3e7a6576e717d903aa8109bd2a8a";

ServerConnectivityHelper serverConnectivityHelper = new ServerConnectivityHelperImpl(host, port);
SafewalkClient client = new SafewalkClientImpl(serverConnectivityHelper);
AuthenticationResponse response = client.authenticate(accessToken, "admin", "admin");
```
* host : The server host
* port : The server port
* accessToken : The access token of the system user created to access this api in the superadmin console 

### Response Examples (AuthenticationResponse class)

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
