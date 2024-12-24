package com.total650.springboot_hub.repository;

import com.total650.springboot_hub.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    Boolean existsByName(String name);
}
