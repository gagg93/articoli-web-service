package com.si2001.webapp.service;

import com.si2001.webapp.entity.User;
import com.si2001.webapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {

        return repository.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    @Override
    public void updateUser(User user) throws Exception {
        Optional<User> temp = repository.findUserByUsername(user.getUsername());
        if (!temp.isPresent() || temp.get().getId() == user.getId()) {
            repository.save(user);
        }else{
            throw new Exception("Username occupato");
        }
    }

    @Override
    public void deleteUser(int id) {
        repository.deleteById(id);
    }

    @Override
    public User findByUsename(String username) {
        Optional<User> user = repository.findUserByUsername(username);
        return user.orElse(null);
    }


}
