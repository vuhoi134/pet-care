package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.UserDTO;
import com.poly.petcare.domain.entites.Profile;
import com.poly.petcare.domain.entites.Role;
import com.poly.petcare.domain.entites.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServices extends BaseServices {
    public ResponseEntity<?> create(UserDTO dto){
        Profile profile = Profile.builder().build();
        Role role = Role.builder().build();
        User user = User.builder()
                .userName(dto.getUserName())
                .passWord(pa)
                .profile(profile)
                .role(role)
    }
}
