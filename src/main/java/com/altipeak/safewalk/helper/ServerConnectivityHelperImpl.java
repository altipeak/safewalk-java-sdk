package com.altipeak.safewalk.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ServerConnectivityHelperImpl implements ServerConnectivityHelper {

    private static final int DEFAULT_TIMEOUT = 30000;
    
    private final String host;
    private final long port;
    private static boolean bypassSSL = false;
    
    public ServerConnectivityHelperImpl(final String host, final long port) {
        this.host = host;
        this.port = port;
    }
    
    public ServerConnectivityHelperImpl(final String host, final long port, final boolean bypassSSLCheck) {
        this.host = host;
        this.port = port;
        this.bypassSSL = bypassSSLCheck;
    }
    
    // ************************************
    // * Public Methods
    // ************************************
    
    public Response post(String path, Map<String, String> parameters, Map<String, String> headers) throws ConnectivityException {
        return doRequest("POST", path, parameters, headers);
    }

    public Response put(String path, Map<String, String> parameters, Map<String, String> headers) throws ConnectivityException {
        return doRequest("PUT", path, parameters, headers);
    }
    
    public Response get(String path, Map<String, String> parameters, Map<String, String> headers) throws ConnectivityException {
        return doRequest("GET", path, parameters, headers);
    }
    
    public Response delete(String path, Map<String, String> parameters, Map<String, String> headers) throws ConnectivityException {
        return doRequest("DELETE", path, parameters, headers);
    }
    // ************************************
    // * Private Methods
    // ************************************
    
    
    private Response doRequest(String method, String path, Map<String, String> parameters, Map<String, String> headers) throws ConnectivityException  {
        
        /* Accept self-signed certificates */
        SSLContext ctx;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] {
                    new X509TrustManager() {
                      public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                      public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                      public X509Certificate[] getAcceptedIssuers() { if(bypassSSL) return null; else return new X509Certificate[]{}; }
                    }
                  }, new java.security.SecureRandom());
              HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());   
              
              /* the name on the certificate doesn't match the hostname */
              HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                  public boolean verify(String hostname, SSLSession session) {
                    return true;
                  }          
              });
              
              HttpURLConnection connection = null;
              String url = (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")) ? 
                      String.format("%s:%s%s", host, port, path):
                          String.format("%s:%s%s?%s", host, port, path, this.urlEncode(parameters));
              URL serverAddress = new URL(url);
              connection = (HttpURLConnection)serverAddress.openConnection();
              connection.setRequestProperty("Content-Type", 
                      "application/x-www-form-urlencoded");
              if (headers != null) {
                  for (Entry<String, String> entry : headers.entrySet()) {
                      connection.setRequestProperty(entry.getKey(), entry.getValue());
                  }
              }
              connection.setRequestMethod(method);
              connection.setConnectTimeout(DEFAULT_TIMEOUT);
              connection.setReadTimeout(DEFAULT_TIMEOUT);
              if ( method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT") ) {
                  connection.setDoOutput(true);
                  connection.getOutputStream().write(this.urlEncode(parameters).getBytes());
                  connection.connect();
              }
              
              int responseCode = this.getResponseCode(connection);
              
              final BufferedReader rd;
              if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_NO_CONTENT)  {
                  rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
              }else{
                  rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
              }
              
              StringBuilder sb = new StringBuilder();
              String line = null;
              while ((line = rd.readLine()) != null){                
                  sb.append(line).append('\n');
              }
              return new Response(sb.toString(), responseCode);
                  
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConnectivityException(e);
        } catch (KeyManagementException e) {
            throw new ConnectivityException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new ConnectivityException(e);
        }
    }
    
    private int getResponseCode(final HttpURLConnection connection) throws IOException {
        int responseCode = 0;
        try {
            responseCode = connection.getResponseCode();
        }catch(IOException e){
            responseCode = connection.getResponseCode();
        }
        return responseCode;
    }
    
    private String urlEncode(Map<String, String> query) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for (Entry<String, String> entry : query.entrySet()) {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            builder.append("&");
        }
        return builder.toString();
    }
     
}
