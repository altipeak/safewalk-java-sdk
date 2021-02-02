package com.altipeak.safewalk;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GetUserResponse {

    private final String dbMobilePhone;
    private final String dbEmail;
    private final String ldapMobilePhone;
    private final String ldapEmail;
    private final UserStorage userStorage;
    private final String firstName;
    private final String lastName;
    private final String dn;
    private final String username;
    private final Boolean locked;
    
    private final int httpCode;
    
    private final Map<String, List<String>> errors;
    
    private static final String SEPARATOR = " | ";
    
    public enum UserStorage {
        LDAP,
        DB,
    }
    
    // ************************************
    // * Constructors
    // ************************************
    
    /*package*/ GetUserResponse(int httpCode
                            , String username
                            , String firstName
                            , String lastName
                            , String dn
                            , String dbMobilePhone
                            , String dbEmail
                            , String ldapMobilePhone
                            , String ldapEmail
                            , UserStorage userStorage
                            , Boolean locked){
        
        this.dbMobilePhone = dbMobilePhone;
        this.dbEmail = dbEmail;
        this.ldapMobilePhone = ldapMobilePhone;
        this.ldapEmail = ldapEmail;
        this.userStorage = userStorage;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dn = dn;
        this.username = username;
        this.httpCode = httpCode;
        this.locked = locked;
        this.errors = Collections.emptyMap();
        
    }
    
    /*package*/ GetUserResponse(int httpCode, Map<String, List<String>> errors){
        this.dbMobilePhone = null;
        this.dbEmail = null;
        this.ldapMobilePhone = null;
        this.ldapEmail = null;
        this.userStorage = null;
        this.firstName = null;
        this.lastName = null;
        this.dn = null;
        this.username = null;
        this.httpCode = httpCode;
        this.errors = errors;
        this.locked = null;
    }

    // ************************************
    // * Public Methods
    // ************************************

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(this.httpCode)).append(SEPARATOR);
        if ( this.httpCode == 200 ) {
            sb.append(this.username).append(SEPARATOR);
            sb.append(this.firstName).append(SEPARATOR);
            sb.append(this.lastName).append(SEPARATOR);
            sb.append(this.userStorage).append(SEPARATOR);
            sb.append(this.dn).append(SEPARATOR);
            sb.append(this.dbEmail).append(SEPARATOR);
            sb.append(this.dbMobilePhone).append(SEPARATOR);
            sb.append(this.ldapEmail).append(SEPARATOR);
            sb.append(this.ldapMobilePhone).append(SEPARATOR);
            sb.append(this.locked).append(SEPARATOR);
        }else{
            for (Entry<String, List<String>> errors : this.errors.entrySet()) {
                sb.append(errors.getKey()).append(" [");
                for (String error : errors.getValue()) {
                    sb.append(error).append(", ");
                }
                sb.append("]").append(SEPARATOR);
            }
        }
        return sb.toString();
    }
    
    
    /**
     * @return the errors
     */
    public Map<String, List<String>> getErrors() {
        return errors;
    }

    /**
     * @return the dbMobilePhone
     */
    public String getDbMobilePhone() {
        return dbMobilePhone;
    }

    /**
     * @return the dbEmail
     */
    public String getDbEmail() {
        return dbEmail;
    }

    /**
     * @return the ldapMobilePhone
     */
    public String getLdapMobilePhone() {
        return ldapMobilePhone;
    }

    /**
     * @return the ldapEmail
     */
    public String getLdapEmail() {
        return ldapEmail;
    }

    /**
     * @return the userStorage
     */
    public UserStorage getUserStorage() {
        return userStorage;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the dn
     */
    public String getDn() {
        return dn;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the locked
     */
    public Boolean isLocked() {
        return locked;
    }
        
    
}
