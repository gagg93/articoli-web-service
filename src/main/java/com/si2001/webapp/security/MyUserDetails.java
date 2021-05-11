package com.si2001.webapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean admin;


    public MyUserDetails(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public MyUserDetails() { }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (admin){
            return Collections.singletonList(new SimpleGrantedAuthority(("ROLE_ADMIN")));
        } else{
            return Collections.singletonList(new SimpleGrantedAuthority(("ROLE_USER")));
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isAdmin() {
        return admin;
    }
}
