package com.rizomm.m2.rooforall.rooforall.controllers;


import com.rizomm.m2.rooforall.rooforall.dto.HouseDto;
import com.rizomm.m2.rooforall.rooforall.dto.HouseElementDto;
import com.rizomm.m2.rooforall.rooforall.mappers.HouseMapper;
import com.rizomm.m2.rooforall.rooforall.services.HouseService;
import com.rizomm.m2.rooforall.rooforall.services.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private MinioService minioService;

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

    @DeleteMapping("/{houseId}")
    public void deleteHouse(@PathVariable Long houseId) {
        houseService.deleteHouseById(houseId);
    }

    @PostMapping("/uploadFiles/{houseId}")
    public void uploadFile(@RequestParam("files") MultipartFile[] files, @PathVariable Long houseId) throws Exception {
        minioService.addImagesToHouse(files, houseId);
    }

    @PostMapping("/mainImage/{houseId}")
    public void uploadMainImage(@RequestParam("file") MultipartFile file, @PathVariable Long houseId) throws Exception {
        minioService.addMainImage(file, houseId);
    }

    @DeleteMapping("/deleteFiles/{houseId}")
    public void deleteFiles(@RequestParam List<Long> fileIds, @PathVariable Long houseId) throws Exception {
        minioService.deleteFiles(fileIds, houseId);
    }

}
