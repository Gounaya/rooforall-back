package com.rizomm.m2.rooforall.rooforall.controllers;


import com.rizomm.m2.rooforall.rooforall.dto.HouseDto;
import com.rizomm.m2.rooforall.rooforall.dto.HouseElementDto;
import com.rizomm.m2.rooforall.rooforall.mappers.HouseMapper;
import com.rizomm.m2.rooforall.rooforall.services.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/houses")
@Validated
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private HouseMapper houseMapper;

    @GetMapping("/{houseId}")
    public HouseDto getHouseById(@PathVariable Long houseId) {
        return houseMapper.toDto(houseService.findHouseById(houseId));
    }

    @GetMapping
    public List<HouseElementDto> findAllHouses() {
        return houseMapper.toElementDtos(houseService.findAllHouses());
    }

    @PostMapping
    public HouseDto createHouse(@Valid @RequestBody HouseDto houseDto) {
        return houseMapper.toDto(houseService.createHouse(houseMapper.toBo(houseDto)));
    }

    @PutMapping
    public HouseDto editHouse(@Valid @RequestBody HouseDto houseDto) {
        return houseMapper.toDto(houseService.editHouse(houseMapper.toBo(houseDto)));
    }

}
