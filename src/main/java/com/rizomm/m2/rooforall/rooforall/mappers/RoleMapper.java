package com.rizomm.m2.rooforall.rooforall.mappers;


import com.rizomm.m2.rooforall.rooforall.dto.RoleDto;
import com.rizomm.m2.rooforall.rooforall.entites.Role;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toBo(RoleDto roleDto);

    List<Role> toBos(List<RoleDto> roleDtoList);

    RoleDto toDto(Role role);

    List<RoleDto> toDtos(List<Role> roleList);

}
