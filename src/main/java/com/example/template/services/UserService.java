package com.example.template.services;

import com.example.template.models.Role;
import com.example.template.models.User;
import com.example.template.repositories.RoleRepository;
import com.example.template.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User createUser(String username, String email, String rawPassword, Date birthday, Set<String> roleNames) {
        //TODO Raw password must be hashed before saving

        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName).orElseThrow( () -> new RuntimeException("Role " + roleName + " not found"));
            roles.add(role);
        }
        User user = new User(username, email, rawPassword, birthday, roles);
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
