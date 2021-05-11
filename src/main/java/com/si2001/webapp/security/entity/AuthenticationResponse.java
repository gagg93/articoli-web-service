package com.si2001.webapp.security.entity;

public class AuthenticationResponse {
    private final String jwt;
    private final String username;
    private final boolean admin;

    public AuthenticationResponse(String jwt, String username, boolean admin) {
        this.jwt = jwt;
        this.username = username;
        this.admin = admin;
    }

    public boolean getAdmin() {
        return admin;
    }

    public String getJwt() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }

}
