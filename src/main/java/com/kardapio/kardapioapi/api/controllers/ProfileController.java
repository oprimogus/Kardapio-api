package com.kardapio.kardapioapi.api.controllers;

import com.kardapio.kardapioapi.domain.profile.dto.ProfileDTO;
import com.kardapio.kardapioapi.domain.profile.service.ProfileService;
import com.kardapio.kardapioapi.domain.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;


    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("")
    public ProfileDTO findMe() {
        return this.profileService.findProfile();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileDTO createProfile (@Valid @RequestBody ProfileDTO profile) {
        return this.profileService.createProfile(profile);
    }

    @PutMapping("")
    public ProfileDTO updateProfile (@Valid @RequestBody ProfileDTO profile) {
        return this.profileService.updateProfile(profile);
    }


}
