package com.si2001.webapp.service;

import com.si2001.webapp.dto.UserDto;
import com.si2001.webapp.entity.User;
import com.si2001.webapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
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
    public void updateUser(UserDto userDto) throws Exception {
        User user = new User();
        user.setAdmin(userDto.isAdmin());
        user.setUsername(userDto.getUsername());
        if (userDto.getId()== 0){
            user.setPassword(encoder.encode(userDto.getPassword()));
        }
        user.setBirthDate(userDto.getBirthDate());
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        Optional<User> temp = repository.findUserByUsername(user.getUsername());
        if (!temp.isPresent() || temp.get().getId() == user.getId()) {
            if (temp.isPresent() && temp.get().getId() == user.getId()){
                user.setPassword(temp.get().getPassword());
                user.setReservationSet(temp.get().getReservationSet());
            }
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
    public User findByUsername(String username) {
        Optional<User> user = repository.findUserByUsername(username);
        return user.orElse(null);
    }


}
