# JAVA SDK module for Safewalk integration

* [Authentication API](#authentication-api)

<a name="authentication-api"></a>
## Authentication API

This project presents the Safewalk Authentication API usage. The available APIs are listed below: 

* User Credentials 
* QR Code 
* Push Signature 
* Push Authentication 
* External Password Authentication

It contains an example client APP, and a .Jar/DLL inside, with the methods that perform the authentication against the plataform. 

Note, Inside /src/main/java/com/altipeak/safewalk/SafewalkClient.java there is the description of each method and the required/optional parameters to call them. 

### Usage

```java
String host = "https://safewalk_address...";
long  port = 8445;
String AUTHENTICATION_API_ACCESS_TOKEN = "c4608fc697e844829bb5a27cce13737250161bd0";
String username = "user@mycompany.com";
String mobileUsername = "mobileuser@mycompany.com";
    
    SafewalkClient client = new SafewalkClientImpl(this.serverConnectivityHelper, null, AUTHENTICATION_API_ACCESS_TOKEN);
    // Example 1: User credentials
    AuthenticationResponse response1 = client.authenticate(username, "12345");
    System.out.println("USER CREDENTIALS AUTHENTICATION RESPONSE : " + response1);
    // Example 2: Push Signature
    SignatureResponse response2 = client.sendPushSignature(mobileUsername,"abcde", "A160E4F805C51261541F0AD6BC618AE10BEB3A30786A099CE67DBEFD4F7F929F","All the data here will be signed. This request was generated from Safewalk API.","Sign Transaction","Push signature triggered from safewalk API");
    System.out.println("PUSH SIGNATURE RESPONSE OPTION 1: " + response2);
```
* host : The server host.
* port : The server port.
* username : An LDAP or internal user with no licenses registered and password authentication allowed. 
* mobileUsername : An LDAP or internal user registered with a Fast:Auth:Sign license.

### Authentication API Access Token
 
Before you can start using the Safewalk OAuth2 Restful API you will need to generate an authentication access-token (key) that will allow access to the different API.
Follow the instructions below to create a system user with keys to access the API:
* Access the Safewalk Appliance using an ssh client (e.g putty)
* Execute the following commands to create/update a system user with API keys: 

source /home/safewalk/safewalk-server-venv/bin/activate
 django-admin.py create_system_user --username <username> --auth-api-accesstoken --settings=gaia_server.settings

You will see an output similar to the one bellow:
  authentication-api : 1be0fd6a24fc508f45a184a87f3fc466d0c2603c . Created
*  Execute the following command if you want to list the existing API access tokens of a user:
 
 source /home/safewalk/safewalk-server-venv/bin/activate django-admin.py
  create_system_user --username <username> --settings=gaia_server.settings
* Copy the access-token that was generated for the authentication-api and save it so you’ll be able to use it to make the API calls
