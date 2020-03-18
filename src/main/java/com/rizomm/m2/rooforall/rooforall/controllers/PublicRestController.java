package com.rizomm.m2.rooforall.rooforall.controllers;

import com.rizomm.m2.rooforall.rooforall.dto.UserInfo;
import com.rizomm.m2.rooforall.rooforall.entites.User;
import com.rizomm.m2.rooforall.rooforall.mappers.UserMapper;
import com.rizomm.m2.rooforall.rooforall.repositories.UserRepository;
import com.rizomm.m2.rooforall.rooforall.security.UserPrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/public")
@CrossOrigin
public class PublicRestController {

    @Autowired
    private UserPrincipalDetailService userService;

    @Autowired
    private UserMapper userMapper;


    // all users
    @GetMapping("/test1")
    public String test1() {
        return "API test 1";
    }

    // for managers
    @GetMapping("management/reports")
    public String test2() {
        return "Some report data";
    }

    // for ROLE_ADMIN
    @GetMapping("admin/users")
    public List<UserInfo> users() {
        return userMapper.toUsersInfo(userService.fetchAllUsers());
    }
}
