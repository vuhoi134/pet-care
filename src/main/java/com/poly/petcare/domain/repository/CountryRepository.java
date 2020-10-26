package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,Long> {

}
