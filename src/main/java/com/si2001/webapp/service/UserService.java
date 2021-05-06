package com.si2001.webapp.service;

import com.si2001.webapp.entity.User;

import java.util.List;

public interface UserService {
        List<User> findAll();

        User findById(int id);

        void updateUser(User user) throws Exception;

        void deleteUser(int id);

        User findByUsename(String username);

}
