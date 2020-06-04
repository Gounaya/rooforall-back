package com.rizomm.m2.rooforall.rooforall.services;

import com.rizomm.m2.rooforall.rooforall.entites.House;
import com.rizomm.m2.rooforall.rooforall.repositories.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;


    public House createHouse(House house) {
        house.setId(null);
        return houseRepository.save(house);
    }

    public House findHouseById(Long housId) {
        return houseRepository.findById(housId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "There is no house with Id = " + housId));
    }

    public House editHouse(House house) {
        House existingHouse = houseRepository.findById(house.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No record found for house id " + house.getId()));

        return houseRepository.save(house);
    }

    public List<House> findAllHouses() {
        return houseRepository.findAll();
    }

    public void deleteHouseById(Long houseId) {
        House existingHouse = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No record found for house id " + houseId));

        houseRepository.delete(existingHouse);
    }
}
