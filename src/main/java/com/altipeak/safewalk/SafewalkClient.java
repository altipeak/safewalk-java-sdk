package com.altipeak.safewalk;

import com.altipeak.safewalk.helper.ServerConnectivityHelper.ConnectivityException;

/**
 * Safewalk integration Client
 */
public interface SafewalkClient {
    
    AuthenticationResponse authenticate(String accessToken, String username, String password) throws ConnectivityException;
    
    AuthenticationResponse authenticate(String accessToken, String username, String password, String transactionId) throws ConnectivityException;
    
}
