package com.altipeak.safewalk.helper;


import java.util.Map;

public interface ServerConnectivityHelper {
    
    Response post(String url, Map<String, String> parameters, Map<String, String> headers) throws ConnectivityException;
 
    public class ConnectivityException extends Exception {
        private static final long serialVersionUID = 1L;
        
        public ConnectivityException(Exception cause) {
            super(cause);
        }
    }
    
    public class Response {
        
        private final String content;
        private final int responseCode;
        
        public Response(final String content, final int responseCode) {
            this.content = content;
            this.responseCode = responseCode;
        }

        public String getContent() {
            return content;
        }

        public int getResponseCode() {
            return responseCode;
        }
        
    }
    
}
