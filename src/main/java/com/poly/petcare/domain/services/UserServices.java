package com.poly.petcare.domain.services;

import com.poly.petcare.app.contant.RoleIdConstant;
import com.poly.petcare.app.dtos.ProfileDTO;
import com.poly.petcare.app.dtos.UserDTO;
import com.poly.petcare.app.responses.UserResponses;
import com.poly.petcare.app.result.DataApiResult;
import com.poly.petcare.domain.entites.Profile;
import com.poly.petcare.domain.entites.User;
import com.poly.petcare.domain.entites.UserRole;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServices extends BaseServices {
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<?> create(UserDTO dto) {
        Profile profile = new Profile();
        User user = new User();
        user.setUserName(dto.getUserName());
        if (userRepository.existsByUserName(dto.getUserName())) {
            throw new ResourceNotFoundException("This username already exists");
        }
        user.setPassWord(passwordEncoder.encode(dto.getPassWord()));
        user.setProfile(profile);
        userRepository.save(user);

//        UserRole userRole = UserRole.builder()
//                .userId(user.getId())
//                .roleId(RoleIdConstant.Role_User)
//                .build();
//        userRoleRepository.save(userRole);

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(RoleIdConstant.Role_User);
        userRoleRepository.save(userRole);
        return ResponseEntity.ok(true);
    }
}
