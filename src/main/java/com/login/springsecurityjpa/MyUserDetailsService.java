package com.login.springsecurityjpa;

import com.login.springsecurityjpa.Models.MyUserDetails;
import com.login.springsecurityjpa.Models.Role;
import com.login.springsecurityjpa.Models.User;
import com.login.springsecurityjpa.Repositories.RoleRepository;
import com.login.springsecurityjpa.Repositories.UserRepository;
import com.login.springsecurityjpa.Repositories.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }

}
