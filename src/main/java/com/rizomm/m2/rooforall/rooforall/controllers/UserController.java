package com.rizomm.m2.rooforall.rooforall.controllers;

import com.rizomm.m2.rooforall.rooforall.dto.UserInfo;
import com.rizomm.m2.rooforall.rooforall.dto.UserSignUpDto;
import com.rizomm.m2.rooforall.rooforall.mappers.UserMapper;
import com.rizomm.m2.rooforall.rooforall.security.UserPrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private UserPrincipalDetailService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/signUp")
    public UserInfo signUp(@RequestBody @Valid UserSignUpDto signUpDto) {
        return userMapper.toUserInfo(userService.createAccount(userMapper.toBo(signUpDto)));
    }

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
