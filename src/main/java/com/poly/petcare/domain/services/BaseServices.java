package com.poly.petcare.domain.services;

import com.poly.petcare.domain.mapper.ModelMapper;
import com.poly.petcare.domain.repository.*;
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
    protected UserRepository userRepository;
    @Autowired
    protected UserRoleRepository userRoleRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
}
