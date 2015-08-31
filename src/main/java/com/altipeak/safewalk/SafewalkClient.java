package com.altipeak.safewalk;

import com.altipeak.safewalk.helper.ServerConnectivityHelper.ConnectivityException;

/**
 * Safewalk integration Client
 */
public interface SafewalkClient {
    
    AuthenticationResponse authenticate(String accessToken, String username, String password) throws ConnectivityException;
    
    AuthenticationResponse authenticate(String accessToken, String username, String password, String transactionId) throws ConnectivityException;
    
    CreateUserResponse createUser(String accessToken, String username, String password, String firstName, String lastName, String mobilePhone, String email, String parent) throws ConnectivityException;
 
    UpdateUserResponse updateUser(String accessToken, String username, String mobilePhone, String email) throws ConnectivityException;
    
    GetUserResponse getUser(String accessToken, String username) throws ConnectivityException;
    
    DeleteUserResponse deleteUser(String accessToken, String username) throws ConnectivityException;
    
    SetStaticPasswordResponse setStaticPassword(String accessToken, String username, String password) throws ConnectivityException;
    
    AssociateTokenResponse associateToken(String accessToken, String username, DeviceType deviceType) throws ConnectivityException;
    
    AssociateTokenResponse associateToken(String accessToken, String username, DeviceType deviceType, Boolean sendRegistrationCode, Boolean sendDownloadLinks) throws ConnectivityException;
    
    GetTokenAssociationsResponse getTokenAssociations(String accessToken, String username) throws ConnectivityException;
    
    DeleteTokenAssociation deleteTokenAssociation(String accessToken, String username, DeviceType deviceType, String serialNumber) throws ConnectivityException;
    
}
