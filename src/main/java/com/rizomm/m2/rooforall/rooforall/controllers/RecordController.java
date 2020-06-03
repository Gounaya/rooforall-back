package com.rizomm.m2.rooforall.rooforall.controllers;

import com.rizomm.m2.rooforall.rooforall.dto.RecordDto;
import com.rizomm.m2.rooforall.rooforall.dto.UserInfo;
import com.rizomm.m2.rooforall.rooforall.entites.Record;
import com.rizomm.m2.rooforall.rooforall.mappers.RecordMapper;
import com.rizomm.m2.rooforall.rooforall.mappers.UserMapper;
import com.rizomm.m2.rooforall.rooforall.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/records")
@Validated
public class RecordController {

    @Autowired
    RecordService recordService;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{recordId}")
    public RecordDto getRecord(Principal principal, @PathVariable Long recordId) {
        return recordMapper.toDto(recordService.getRecordById(principal.getName(), recordId));
    }

    @PostMapping
    public UserInfo createRecord(Principal principal, @RequestBody @Valid RecordDto recordDto) {
        return userMapper.toUserInfo(recordService.createRecord(principal.getName(), recordMapper.toBo(recordDto)));
    }

    @PutMapping
    public UserInfo editRecord(Principal principal, @RequestBody @Valid RecordDto recordDto) {
        return userMapper.toUserInfo(recordService.editRecord(principal.getName(), recordMapper.toBo(recordDto)));
    }

    @DeleteMapping("/{recordId}")
    public UserInfo deleteRecord(Principal principal, @PathVariable Long recordId) {
        return userMapper.toUserInfo(recordService.deleteRecord(principal.getName(), recordId));
    }

    @DeleteMapping
    public UserInfo deleteAllRecords(Principal principal) {
        return userMapper.toUserInfo(recordService.deleteAllRecords(principal.getName()));
    }

    @PutMapping("/{recordId}")
    public Record submitRecord(Principal principal, @PathVariable Long recordId) {
        return recordService.submitRecord(principal.getName(), recordId);
    }

    @PostMapping("/affect")
    public RecordDto affectHouseToRecord(@RequestParam Long recordId, @RequestParam Long houseId) {
        return recordMapper.toDto(recordService.affectHouseToRecord(recordId, houseId));
    }
}
