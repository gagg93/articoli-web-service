package com.si2001.webapp.controller;

import com.si2001.webapp.entity.User;
import com.si2001.webapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
        public List<User> getUsers() {

            return userService.findAll();
        }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.findById(id);
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findByUsename(username);
    }

    @PostMapping("/users")
    public String getUserById(@RequestBody User user) {
        try{
            userService.updateUser(user);
        }catch (Exception e){
            return e.getMessage();
        }
        return "ok";
    }


    @PutMapping("/users")
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
}
