package com.poly.petcare.domain.services;

import com.poly.petcare.app.dtos.ProfileDTO;
import com.poly.petcare.app.responses.ProfileResponses;
import com.poly.petcare.domain.entites.Profile;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import com.poly.petcare.domain.specification.ProductStoreSpecification;
import com.poly.petcare.domain.specification.ProfileSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        profile.setImage(dto.getImages());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setGender(dto.getGender());
        profile.setBirthDay(dto.getBirthDay());
        profileRepository.saveAndFlush(profile);
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
}
