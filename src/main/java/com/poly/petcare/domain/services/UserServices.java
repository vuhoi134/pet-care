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
import com.poly.petcare.domain.specification.ProductStoreSpecification;
import com.poly.petcare.domain.specification.ProfileSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        userRole.setRoleId(2L);
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

    public ResponseEntity<?> forgotPassword(String forgot) {
        String pass=String.valueOf(new Date().getTime());
        Specification conditions = Specification.where(ProfileSpecification.hasProfileByEmail(forgot).or(
                ProfileSpecification.hasProfileByPhone(forgot)));
        Optional<Profile> profile=profileRepository.findOne(conditions);
        User user=userRepository.getOne(profile.get().getUser().getId());
        user.setPassWord(passwordEncoder.encode(pass));
        userRepository.saveAndFlush(user);
        String textPassword="Mật khẩu mới của quý khách là: "+pass;
        if(forgot.contains("@")) {
            if (mailSerivce.sendSimpleEmail(forgot, "Thông tin tài khoản", textPassword)) {
                return ResponseEntity.ok(true);
            }
        }else{
            if (mailSerivce.sendSimpleEmail(profile.get().getEmail(), "Thông tin tài khoản", textPassword)) {
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.ok(false);
    }

}
