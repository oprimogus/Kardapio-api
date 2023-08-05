package com.kardapio.kardapioapi.domain.profile.services;

import com.kardapio.kardapioapi.domain.profile.dto.ProfileDTO;
import com.kardapio.kardapioapi.domain.profile.dto.ProfileMapper;
import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.profile.repository.ProfileRepository;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import com.kardapio.kardapioapi.domain.user.repository.UserRepository;
import com.kardapio.kardapioapi.exceptions.RecordConflictException;
import com.kardapio.kardapioapi.exceptions.RecordNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProfileService {

    private static final String ERROR_MESSAGE = "profile not found.";

    private final ProfileRepository profileRepository;

    private final UserRepository userRepository;

    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository,
                          ProfileMapper profileMapper,
                          UserRepository userRepository){
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.userRepository = userRepository;
    }

    public ProfileModel findProfile(@NotNull Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ERROR_MESSAGE));
    }

    @Transactional
    public ProfileDTO createProfile(@Valid ProfileDTO profileDTO, UserModel userModel) {
        Optional<ProfileModel> profile = Optional.ofNullable(userModel.getProfileModel());
        if (profile.isPresent()) {
            throw new RecordConflictException("This user already has a profile.");
        }
        ProfileModel newProfile = profileMapper.toModel(profileDTO, userModel.getId());
        userModel.setProfileModel(newProfile);
        this.userRepository.save(userModel);
        this.profileRepository.save(newProfile);
        return profileMapper.toDTO(newProfile);
    }

    public ProfileModel updateProfile(@Valid @NotNull ProfileModel profileModel) {
        return profileRepository.findById(profileModel.getId())
                .orElseThrow(() -> new RecordNotFoundException(ERROR_MESSAGE));

    }


}
