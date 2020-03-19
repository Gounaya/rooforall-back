package com.rizomm.m2.rooforall.rooforall.dto;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    private String username;

    private String email;

    private int active;

    private Collection<RoleDto> roles;

}
