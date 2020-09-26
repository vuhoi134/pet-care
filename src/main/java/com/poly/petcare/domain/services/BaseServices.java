package com.poly.petcare.domain.services;

import com.poly.petcare.domain.mapper.ModelMapper;
import com.poly.petcare.domain.repository.CategoryAttributeRepository;
import com.poly.petcare.domain.repository.CategoryAttributeValueRepository;
import com.poly.petcare.domain.repository.CategoryRepository;
import com.poly.petcare.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BaseServices {
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected CategoryAttributeRepository categoryAttributeRepository;
    @Autowired
    protected CategoryAttributeValueRepository categoryAttributeValueRepository;
    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected ModelMapper modelMapper;


    @Autowired
    protected PasswordEncoder passwordEncoder;
}
