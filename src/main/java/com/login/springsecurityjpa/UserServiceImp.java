package com.login.springsecurityjpa;

import com.login.springsecurityjpa.Models.Role;
import com.login.springsecurityjpa.Models.User;
import com.login.springsecurityjpa.Repositories.RoleRepository;
import com.login.springsecurityjpa.Repositories.UserRepository;
import com.login.springsecurityjpa.Repositories.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByname("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    @Override
    public boolean isUserAlreadyPresent(User user) {
        boolean isUserAlreadyExists = false;
        User existingUser = userRepository.getUserByUsername(user.getUsername());
        // If user is found in database, then then user already exists.
        if(existingUser != null){
            isUserAlreadyExists = true;
        }
        return isUserAlreadyExists;
    }

    @Override
    public User isEmailAlreadyPresent(User user) {
        boolean isUserAlreadyExists = false;
        User existingUser = userRepository.getUserByEmail(user.getEmail());
        // If user is found in database, then then user already exists.
        if(existingUser != null){
            //isUserAlreadyExists = true;
            return existingUser;
        }
        return null;
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public void saveEditUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);  //Always do - SET FOREIGN_KEY_CHECKS=0; IN MYsql
    }
}
