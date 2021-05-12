package com.si2001.webapp.controller;

import com.si2001.webapp.dto.UserDto;
import com.si2001.webapp.entity.User;
import com.si2001.webapp.security.JwtUtil;
import com.si2001.webapp.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class UserController {


    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/users")
        public ResponseEntity<List<User>> getUsers() {

            return new ResponseEntity<>(userService.findAll(), new HttpHeaders(), HttpStatus.OK);
        }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.findByUsername(username), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/users/new")
    public ResponseEntity<String> getUserById(@RequestBody UserDto user) {
        try{
            userService.updateUser(user);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("ok", new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(userService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> editUserById(@RequestBody UserDto user){
        try{
            userService.updateUser(user);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("ok", new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>( new HttpHeaders(), HttpStatus.OK);
    }

    //metodi customer
    @GetMapping("/users/my/profile")
    public ResponseEntity<User> getProfile(HttpServletRequest httpServletRequest) {
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String username;
        String jwt;
        if (authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")){
            jwt= authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            return new ResponseEntity<>(userService.findByUsername(username), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/users/my/profile")
    public ResponseEntity<?> editProfile(@RequestBody UserDto user, HttpServletRequest httpServletRequest){
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String username;
        String jwt;
        if (authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")){
            jwt= authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            if (username.equals(user.getUsername())){
                try{
                    userService.updateUser(user);
                }catch (Exception e){
                    return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
