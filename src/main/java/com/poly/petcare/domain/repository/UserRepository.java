package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByUserName(String username);
    User findByUserName(String username);
}
