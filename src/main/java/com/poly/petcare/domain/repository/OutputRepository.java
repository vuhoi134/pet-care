package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.Output;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OutputRepository extends JpaRepository<Output,Long>, JpaSpecificationExecutor<Output> {
    @Query(value = "select convert(SUBSTRING_INDEX(code,'OP',-1) , UNSIGNED INTEGER) as c from dbo_output where code like '%OP%' order by c desc limit 0,1",nativeQuery = true)
    Long getCodeMax();
}
