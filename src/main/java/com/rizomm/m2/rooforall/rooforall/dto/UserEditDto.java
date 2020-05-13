package com.rizomm.m2.rooforall.rooforall.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEditDto {

    private String username;

    private String password;

    private byte[] picture;
}
