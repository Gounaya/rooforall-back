package com.rizomm.m2.rooforall.rooforall.security;

import com.rizomm.m2.rooforall.rooforall.entites.User;
import com.rizomm.m2.rooforall.rooforall.repositories.RoleRepository;
import com.rizomm.m2.rooforall.rooforall.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserPrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // get user from DB and convert it to UserDetails object
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        return UserPrincipal.builder().user(user.get()).build();
    }

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }


    public User fetchUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResponseStatusException(NOT_FOUND, "No user found with username " + username);
        }
    }

    public User createAccount(User user) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST,"There is an account with that email adress: " + user.getEmail());
        }

        if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST,"There is an account with that username: " + user.getUsername());
        }

        return userRepository.save(User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .active(1)
                .roles(Arrays.asList(roleRepository.findRoleByName("USER").get()))
                .build());

    }
}
