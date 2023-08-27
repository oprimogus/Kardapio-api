package com.kardapio.kardapioapi.api.controllers;

import com.kardapio.kardapioapi.domain.profile.dto.ProfileDTO;
import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.profile.service.ProfileService;
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
    public ProfileModel findMe() {
        Long id = this.userService.getUserByAuth().getProfileModel().getId();
        return this.profileService.findProfile(id);
    }

    @PostMapping("")
    public ProfileDTO createProfile (@Valid @RequestBody ProfileDTO profile) {
        UserModel auth = this.userService.getUserByAuth();
        return this.profileService.createProfile(profile, auth);
    }


}
