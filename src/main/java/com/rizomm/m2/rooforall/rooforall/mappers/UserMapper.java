package com.rizomm.m2.rooforall.rooforall.mappers;


import com.rizomm.m2.rooforall.rooforall.dto.UserInfo;
import com.rizomm.m2.rooforall.rooforall.dto.UserSignUpDto;
import com.rizomm.m2.rooforall.rooforall.entites.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {RoleMapper.class, RecordMapper.class})
public interface UserMapper {

    UserInfo toUserInfo(User user);

    List<UserInfo> toUsersInfo(List<User> users);

    User toBo(UserSignUpDto userSignUpDto);

    UserSignUpDto toDto(User user);

}
