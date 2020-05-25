package com.rizomm.m2.rooforall.rooforall.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    private String username;

    private String email;

    private boolean active;

    private byte[] picture;

    private Collection<RoleDto> roles;

    private UserInfo supervisor;

    private List<RecordDto> records;
}
