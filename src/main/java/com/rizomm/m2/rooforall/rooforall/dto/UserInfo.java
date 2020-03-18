package com.rizomm.m2.rooforall.rooforall.dto;

import com.rizomm.m2.rooforall.rooforall.entites.Role;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    private String username;

    private int active;

    private Collection<RoleDto> roles;

}
