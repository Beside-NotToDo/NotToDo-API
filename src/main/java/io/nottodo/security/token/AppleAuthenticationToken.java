package io.nottodo.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AppleAuthenticationToken  extends AbstractAuthenticationToken {
    
    private final Object principal;
    private final Object credentials;
    private final String name;
    

    public AppleAuthenticationToken(Object principal, Object credentials,  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.name = null;
        setAuthenticated(true);
    }
    
    public AppleAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.name = null;
        setAuthenticated(false);
    }
    
    public AppleAuthenticationToken(Object principal, Object credentials, String name) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.name = name;
        setAuthenticated(false);
    }
    
    
    @Override
    public Object getCredentials() {
        return this.credentials;
    }
    
    @Override
    public Object getPrincipal() {
        return this.principal;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
}
