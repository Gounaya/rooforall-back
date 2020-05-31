package com.rizomm.m2.rooforall.rooforall.mappers;


import com.rizomm.m2.rooforall.rooforall.dto.HouseDto;
import com.rizomm.m2.rooforall.rooforall.dto.HouseElementDto;
import com.rizomm.m2.rooforall.rooforall.entites.House;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface HouseMapper {

    HouseDto toDto(House house);

    List<HouseDto> toDtos(List<House> houses);

    House toBo(HouseDto houseDto);

    List<House> toBos(List<HouseDto> houseDtos);

    List<HouseElementDto> toElementDtos(List<House> houses);


}
