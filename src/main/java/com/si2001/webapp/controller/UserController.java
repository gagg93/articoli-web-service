package com.si2001.webapp.controller;

import com.si2001.webapp.entity.User;
import com.si2001.webapp.security.JwtUtil;
import com.si2001.webapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {


    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/users")
        public List<User> getUsers() {

            return userService.findAll();
        }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/users/new")
    public String getUserById(@RequestBody User user) {
        try{
            userService.updateUser(user);
        }catch (Exception e){
            return e.getMessage();
        }
        return "ok";
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.findById(id);
    }


    @PutMapping("/users/{id}")
    public String editUserById(@RequestBody User user){
        try{
            userService.updateUser(user);
        }catch (Exception e){
            return e.getMessage();
        }
        return "ok";
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id) {
        userService.deleteUser(id);
    }

    //metodi customer
    @GetMapping("/profile")
    public User getProfile(HttpServletRequest httpServletRequest) {
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String username= null;
        String jwt;
        if (authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")){
            jwt= authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        return userService.findByUsername(username);
    }
}
