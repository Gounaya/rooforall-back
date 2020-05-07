package com.rizomm.m2.rooforall.rooforall.services;

import com.rizomm.m2.rooforall.rooforall.entites.Record;
import com.rizomm.m2.rooforall.rooforall.entites.User;
import com.rizomm.m2.rooforall.rooforall.repositories.RecordRepository;
import com.rizomm.m2.rooforall.rooforall.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class RecordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRepository recordRepository;

    public User createRecord(String username, Record record) {
        User userByUsername = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No user found with username " + username));
        record.setId(null);
        userByUsername.getRecords().add(record);
        return userRepository.save(userByUsername);
    }

    public User editRecord(String username, Record record) {
        User userByUsername = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No user found with username " + username));

        Record existingRecord = recordRepository.findById(record.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No record found for record id " + record.getId()));

        recordRepository.save(record);
        return userByUsername;
    }


    public User deleteRecord(String username, Long recordId) {
        User userByUsername = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No user found with username " + username));
        Record recordById = recordRepository.findById(recordId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No record found with id " + recordId));
        recordRepository.delete(recordById);
        return userByUsername;
    }

    public User deleteAllRecords(String username) {
        User userByUsername = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No user found with username " + username));
        recordRepository.deleteAll();
        return userByUsername;
    }

    public Record getRecordById(String username, Long recordId) {
        User userByUsername = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No user found with username " + username));
        return recordRepository.findById(recordId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No record found with id " + recordId));
    }
}
