package com.rizomm.m2.rooforall.rooforall.repositories;

import com.rizomm.m2.rooforall.rooforall.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(Long Id);

    Optional<User> findUserByEmail(String email);

    List<User> findUsersBySupervisor(User user);
}
