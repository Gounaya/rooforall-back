package com.rizomm.m2.rooforall.rooforall.config;

import com.rizomm.m2.rooforall.rooforall.entites.Role;
import com.rizomm.m2.rooforall.rooforall.entites.User;
import com.rizomm.m2.rooforall.rooforall.repositories.RoleRepository;
import com.rizomm.m2.rooforall.rooforall.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        /**
         * flush database
         */
        userRepository.deleteAll();
        roleRepository.deleteAll();

        Role adminRole = Role.builder()
                .name("ADMIN")
                .build();

        Role userRole = Role.builder()
                .name("USER")
                .build();

        Role advisorRole = Role.builder()
                .name("ADVISOR")
                .build();

        /**
         * Save roles in BD
         */

        List<Role> roleList = Arrays.asList(adminRole, userRole, advisorRole);
        roleRepository.saveAll(roleList);


        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(new ArrayList<>(Arrays.asList(adminRole)))
                .active(1)
                .build();

        User red = User.builder()
                .username("red")
                .password(passwordEncoder.encode("red"))
                .roles(new ArrayList<>(Arrays.asList(userRole)))
                .active(1)
                .build();


        /**
        * Save users in BD
        */
        List<User> userList = Arrays.asList(admin, red);
        userRepository.saveAll(userList);
    }
}
