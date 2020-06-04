package com.rizomm.m2.rooforall.rooforall.repositories;

import com.rizomm.m2.rooforall.rooforall.entites.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
}
