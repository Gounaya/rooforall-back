package com.rizomm.m2.rooforall.rooforall.controllers;

import com.rizomm.m2.rooforall.rooforall.dto.UserInfo;
import com.rizomm.m2.rooforall.rooforall.mappers.UserMapper;
import com.rizomm.m2.rooforall.rooforall.security.UserPrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserPrincipalDetailService userService;

    @Autowired
    private UserMapper userMapper;

  /*  @PostMapping("/signUp")
    public User signUp() {

    }*/

    // for ROLE_ADMIN
    @GetMapping("/listing")
    public List<UserInfo> users() {
        return userMapper.toUsersInfo(userService.fetchAllUsers());
    }

    @GetMapping("/getInfo")
    public UserInfo getUserInfo(@RequestParam String username) {
        return userMapper.toUserInfo(userService.fetchUserByUsername(username));
    }
}
