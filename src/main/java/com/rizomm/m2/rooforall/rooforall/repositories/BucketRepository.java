package com.rizomm.m2.rooforall.rooforall.repositories;


import com.rizomm.m2.rooforall.rooforall.entites.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketRepository extends JpaRepository<Bucket, Long> {

    Optional<Bucket> findByBucketName(String bucketName);
}
