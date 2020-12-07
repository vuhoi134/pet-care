package com.poly.petcare.domain.services;

import com.poly.petcare.app.commom.ChangePasswordVM;
import com.poly.petcare.app.contant.StatesConstant;
import com.poly.petcare.app.dtos.ProfileDTO;
import com.poly.petcare.app.dtos.UserDTO;
import com.poly.petcare.app.responses.ProfileResponses;
import com.poly.petcare.domain.entites.Profile;
import com.poly.petcare.domain.entites.User;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import com.poly.petcare.domain.specification.ProductStoreSpecification;
import com.poly.petcare.domain.specification.ProfileSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileServices extends BaseServices {
    public ResponseEntity<?> edit(ProfileDTO dto, Long profileID) {
        Profile profile = profileRepository.findById(profileID)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Not found profileID" + " " + profileID));
        profile.setFullName(dto.getFullName());
        profile.setAddress(dto.getAddress());
        profile.setEmail(dto.getEmail());
        profile.setImage(dto.getImage());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setGender(dto.getGender());
        profile.setBirthDay(dto.getBirthDay());
        profileRepository.saveAndFlush(profile);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> editProfile(ProfileDTO dto, Long userID) {
        Specification conditions = Specification.where(ProfileSpecification.hasProfile(userID));
        Optional<Profile> profile = profileRepository.findOne(conditions);
        profile.get().setFullName(dto.getFullName());
        profile.get().setAddress(dto.getAddress());
        profile.get().setEmail(dto.getEmail());
        profile.get().setImage(dto.getImage());
        profile.get().setPhoneNumber(dto.getPhoneNumber());
        profile.get().setGender(dto.getGender());
        profile.get().setBirthDay(dto.getBirthDay());
        profileRepository.saveAndFlush(profile.get());
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> info(Long profileID) {
        Profile profile = profileRepository.findById(profileID).orElse(null);
        if (Objects.isNull(profile)) {
            throw new ResourceNotFoundException("Not found profileID" + " " + profileID);
        }
        ProfileResponses responses = modelMapper.profileResponses(profile);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<?> infoByUser(Long userID) {
        Specification conditions = Specification.where(ProfileSpecification.hasProfile(userID));
        Optional<Profile> profile = profileRepository.findOne(conditions);
        if (Objects.isNull(profile)) {
            throw new ResourceNotFoundException("Not found profileID" + " " + userID);
        }
        ProfileResponses responses = modelMapper.profileResponses(profile.get());
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<?> changePassWord(ChangePasswordVM password,String userName) {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userRepository.findByUserName(userName);
        if (passwordEncoder.matches(password.getCurrentPassword(), userEntity.getPassWord()) == true){
            if (password.getNewPassword().equals(password.getConfirmPassword())){
                userEntity.setPassWord(passwordEncoder.encode(password.getNewPassword()));
                userRepository.saveAndFlush(userEntity);
            }
            return ResponseEntity.ok("Mật khẩu đã đổi thành công");
        }return ResponseEntity.ok("Không thành công");

    }
    public List<ProfileResponses> listProfile(boolean status){
        List<User> list =new ArrayList<>(0);
        if(status) {
            list=userRepository.findOneByUserId(StatesConstant.ACTIVE);
        }else{
            list=userRepository.findOneByUserId(StatesConstant.NOTACTIVE);
        }
        List<ProfileResponses> responsesList=new ArrayList<>();
        for (User use:list) {
            ProfileResponses profileResponses=new ProfileResponses();
            profileResponses.setId(use.getId());
            profileResponses.setFullName(use.getProfile().getFullName());
            profileResponses.setPhoneNumber(use.getProfile().getPhoneNumber());
            profileResponses.setEmail(use.getProfile().getEmail());
            profileResponses.setAddress(use.getProfile().getAddress());
            profileResponses.setBirthDay(use.getProfile().getBirthDay());
            profileResponses.setGender(use.getProfile().getGender());
            profileResponses.setImage(use.getProfile().getImage());
            responsesList.add(profileResponses);
        }
        return responsesList;
    }
}
