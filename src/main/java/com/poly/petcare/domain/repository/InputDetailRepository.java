package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.InputDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InputDetailRepository extends JpaRepository<InputDetail,Long> {
    InputDetail findByCodeTag(String codeTag);
}
