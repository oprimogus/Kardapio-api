package com.kardapio.kardapioapi.domain.profile.controller;

import com.kardapio.kardapioapi.domain.profile.dto.ProfileDTO;
import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.profile.services.ProfileService;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import com.kardapio.kardapioapi.domain.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    private final UserService userService;

    public ProfileController(ProfileService profileService,
                             UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping("")
    public ProfileModel findProfile () {
        UserModel user = this.userService.getAuth();
        return this.profileService.findProfile(user.getProfileModel().getId());
    }

    @PostMapping("")
    public ProfileDTO createProfile (@Valid @RequestBody ProfileDTO profile) {
        UserModel auth = this.userService.getAuth();
        return this.profileService.createProfile(profile, auth);
    }


}
