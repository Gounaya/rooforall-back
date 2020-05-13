package com.rizomm.m2.rooforall.rooforall.security;

import com.rizomm.m2.rooforall.rooforall.dto.UserEditDto;
import com.rizomm.m2.rooforall.rooforall.entites.Role;
import com.rizomm.m2.rooforall.rooforall.entites.User;
import com.rizomm.m2.rooforall.rooforall.repositories.RoleRepository;
import com.rizomm.m2.rooforall.rooforall.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class UserPrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // get user from DB and convert it to UserDetails object
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No user found with username " + username));
        return UserPrincipal.builder().user(user).build();
    }

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }


    public User fetchUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No user found with username " + username));
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
                .active(true)
                .roles(Arrays.asList(roleRepository.findRoleByName("USER").get()))
                .build());

    }

    public User assignUserToAdvisor(String advisorName, Long userId) {
        User advisor = userRepository.findUserByUsername(advisorName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "There is no account with that advisor username: " + advisorName));

        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "There is no account with that id: " + userId));

        if(nonNull(user.getSupervisor())) {
            throw new ResponseStatusException(BAD_REQUEST, "This user is already affected this advisor: " + user.getSupervisor().getUsername());
        }

        // get Advisor roles
        List<String> rolesAdvisor = advisor.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        // get the user roles
        List<String> rolesUser = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        // check if an advisor is affecting a user
        if(rolesAdvisor.contains("ADVISOR") && rolesUser.contains("USER") ) {
            user.setSupervisor(advisor);
            return userRepository.save(user);
        } else {
            throw new ResponseStatusException(FORBIDDEN, "You must be an advisor to assign users to you or you are Assigning a non-user account");
        }
    }

    public List<User> getUsersAffectedToAdvisor(String advisorName) {
        User advisor = userRepository.findUserByUsername(advisorName)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "There is no account with that advisor username: " + advisorName));

        return userRepository.findUsersBySupervisor(advisor);
    }

    public User editUser(String username, UserEditDto userEditDto) {
        User userByUsername = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No user found with username " + username));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(!isEmpty(userEditDto.getUsername())) {
            if (userRepository.findUserByUsername(userEditDto.getUsername()).isPresent()) {
                throw new ResponseStatusException(BAD_REQUEST,"There is an account with that username: " + userEditDto.getUsername());
            }

            userByUsername.setUsername(userEditDto.getUsername());
        }

        if(!isEmpty(userEditDto.getPassword())) {
            userByUsername.setPassword(passwordEncoder.encode(userEditDto.getPassword()));
        }

        if(!isEmpty(userEditDto.getPicture())) {
            userByUsername.setPicture(userEditDto.getPicture());
        }

        return userRepository.save(userByUsername);

    }
}
