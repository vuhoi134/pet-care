package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
