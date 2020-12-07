package com.poly.petcare.app.controllers.api;

import com.poly.petcare.app.commom.ChangePasswordVM;
import com.poly.petcare.app.dtos.ProfileDTO;
import com.poly.petcare.app.responses.ProfileResponses;
import com.poly.petcare.domain.entites.User;
import com.poly.petcare.domain.services.ProfileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/profile")
public class ProfileControllerApi {
    @Autowired
    private ProfileServices profileServices;

    @PutMapping("edit/{profileID}")
    public ResponseEntity<?> edit(@Valid @RequestBody ProfileDTO profileDTO, @PathVariable Long profileID) {
        return profileServices.edit(profileDTO, profileID);
    }
    @PutMapping("editProfile/{userId}")
    public ResponseEntity<?> editProfile(@Valid @RequestBody ProfileDTO profileDTO, @PathVariable Long userId) {
        return profileServices.editProfile(profileDTO, userId);
    }
    @PostMapping(value ="changePassWord")
    public ResponseEntity<?> changePassWord(@Valid @RequestBody ChangePasswordVM changePasswordVM,  @RequestParam(name = "userName") String userName){
        return profileServices.changePassWord(changePasswordVM,userName);
    }
    @GetMapping("info/{profileID}")
    public ResponseEntity<?> info(@PathVariable Long profileID) {
        return profileServices.info(profileID);
    }

    @GetMapping("infoByUser/{userId}")
    public ResponseEntity<?> infoByUser(@PathVariable Long userId) {
        return profileServices.infoByUser(userId);
    }
    @GetMapping("listProfile")
    public List<ProfileResponses> listProfile(@RequestParam(name = "status") Boolean status) {
        return profileServices.listProfile(status);
    }
}
