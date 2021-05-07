package com.si2001.webapp.security.controller;


import com.si2001.webapp.security.JwtUtil;
import com.si2001.webapp.security.entity.AuthenticationRequest;
import com.si2001.webapp.security.entity.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtTokenUtil;

    public HelloResource(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @GetMapping(value = "/")
    public String home() {
        return "welcome";
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping(value = "/helloadmin")
    public String helloAdmin() {
        return "hello admin";
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        }catch (BadCredentialsException e){
            throw  new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails= userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, userDetails.getUsername()));

    }

}
