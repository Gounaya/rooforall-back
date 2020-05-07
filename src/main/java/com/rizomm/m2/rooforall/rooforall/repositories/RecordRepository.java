package com.rizomm.m2.rooforall.rooforall.repositories;

import com.rizomm.m2.rooforall.rooforall.entites.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

}
