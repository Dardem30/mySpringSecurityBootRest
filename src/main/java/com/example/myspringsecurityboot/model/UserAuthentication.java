package com.example.myspringsecurityboot.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {
    private final User user;
    private boolean authetificated=true;
    public UserAuthentication(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authetificated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
           this.authetificated=b;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
