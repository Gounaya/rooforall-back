package com.rizomm.m2.rooforall.rooforall.repositories;

import com.rizomm.m2.rooforall.rooforall.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(String roleName);
}
