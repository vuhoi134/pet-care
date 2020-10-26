package com.poly.petcare.domain.services;

import com.poly.petcare.app.contant.RoleIdConstant;
import com.poly.petcare.app.dtos.UserDTO;
import com.poly.petcare.domain.entites.Profile;
import com.poly.petcare.domain.entites.User;
import com.poly.petcare.domain.entites.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServices extends BaseServices {
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<?> create(UserDTO dto) {
        Profile profile = Profile.builder().build();
        User user = User.builder()
                .userName(dto.getUserName())
                .passWord(passwordEncoder.encode(dto.getPassWord()))
                .profile(profile)
                .build();
        userRepository.save(user);
        UserRole userRole = UserRole.builder()
                .userId(user.getId())
                .roleId(RoleIdConstant.Role_User)
                .build();
        userRoleRepository.save(userRole);
        return ResponseEntity.ok(true);
    }
}