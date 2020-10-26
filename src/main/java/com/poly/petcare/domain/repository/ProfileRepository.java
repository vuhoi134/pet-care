package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Long> {

}
