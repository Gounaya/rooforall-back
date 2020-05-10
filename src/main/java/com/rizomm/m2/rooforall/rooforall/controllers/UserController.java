package com.rizomm.m2.rooforall.rooforall.controllers;

import com.rizomm.m2.rooforall.rooforall.dto.UserEditDto;
import com.rizomm.m2.rooforall.rooforall.dto.UserInfo;
import com.rizomm.m2.rooforall.rooforall.dto.UserSignUpDto;
import com.rizomm.m2.rooforall.rooforall.mappers.UserMapper;
import com.rizomm.m2.rooforall.rooforall.security.UserPrincipalDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
@Slf4j
public class UserController {

    @Autowired
    private UserPrincipalDetailService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/signUp")
    public UserInfo signUp(@RequestBody @Valid UserSignUpDto signUpDto) {
        return userMapper.toUserInfo(userService.createAccount(userMapper.toBo(signUpDto)));
    }

    /**
     * List all users.
     * Only for admin.
     *
     * @return the list of all users in the database.
     */
    @GetMapping("/listing")
    public List<UserInfo> users() {
        return userMapper.toUsersInfo(userService.fetchAllUsers());
    }

    /**
     * Get information about the current authenticated user.
     *
     * @param principal the user.
     * @return the user information.
     */
    @GetMapping("/getInfo")
    public UserInfo getUserInfo(Principal principal) {
        return userMapper.toUserInfo(userService.fetchUserByUsername(principal.getName()));
    }

    /**
     * Assign a user to an advisor.
     *
     * @param principal the advisor.
     * @param userId    the id of the user.
     * @return the assigned user.
     */
    @PutMapping("/{userId}")
    public UserInfo assignUserToAdvisor(Principal principal, @PathVariable Long userId) {
        return userMapper.toUserInfo(userService.assignUserToAdvisor(principal.getName(), userId));
    }

    /**
     * List all the user assigned to the current authenticated advisor.
     *
     * @param principal the advisor.
     * @return the list of users.
     */
    @GetMapping("/affectedUsers")
    public List<UserInfo> getUsersAffectedToAdvisor(Principal principal) {
        return userMapper.toUsersInfo(userService.getUsersAffectedToAdvisor(principal.getName()));
    }

    @PutMapping
    public UserInfo editUser(Principal principal, @RequestBody UserEditDto userEditDto) {
        return userMapper.toUserInfo(userService.editUser(principal.getName(), userEditDto));
    }
}
