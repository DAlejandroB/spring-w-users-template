package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String email, String rawPassword, Date birthday){
        //TODO Raw password must be hashed before saving
        User user = new User(username, email, rawPassword, birthday);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean deleteUser(Long id){
        if(getUserById(id).isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
