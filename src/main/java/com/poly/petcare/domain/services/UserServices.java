package com.poly.petcare.domain.services;

import com.poly.petcare.app.contant.RoleIdConstant;
import com.poly.petcare.app.contant.StatesConstant;
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

        User user = new User();
        user.setUserName(dto.getUserName());

        if (userRepository.existsByUserName(dto.getUserName())) {
            throw new ResourceNotFoundException("This username already exists");
        }
        user.setStatus(StatesConstant.ACTIVE);
        user.setPassWord(passwordEncoder.encode(dto.getPassWord()));
        userRepository.save(user);

        Profile profile = new Profile();
        profile.setUser(user);
        profileRepository.save(profile);

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(1L);
        userRoleRepository.save(userRole);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> edit(Long userID){
        User user = userRepository.findById(userID).orElse(null);
        if (Objects.isNull(user)){
            throw new ResourceNotFoundException("Not Found");
        }
        user.setStatus(StatesConstant.NOTACTIVE);
        userRepository.saveAndFlush(user);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> delete(Long userID) {
        User user = userRepository.findById(userID).orElse(null);
        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException("Not found");
        }
        userRepository.delete(user);
        return ResponseEntity.ok(true);
    }

}
