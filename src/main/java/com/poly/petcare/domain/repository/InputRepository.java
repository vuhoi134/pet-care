package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InputRepository extends JpaRepository<Input,Long> {
    @Query(value = "select convert(SUBSTRING_INDEX(code,'IP',-1) , UNSIGNED INTEGER) as c from dbo_input where code like '%IP%' order by c desc limit 0,1",nativeQuery = true)
    Long getCodeMax();
}
