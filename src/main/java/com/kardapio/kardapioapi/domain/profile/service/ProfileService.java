package com.kardapio.kardapioapi.domain.profile.service;

import com.kardapio.kardapioapi.domain.profile.dto.ProfileDTO;
import com.kardapio.kardapioapi.domain.profile.dto.ProfileMapper;
import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.profile.repository.ProfileRepository;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import com.kardapio.kardapioapi.domain.user.repository.UserRepository;
import com.kardapio.kardapioapi.domain.user.services.UserService;
import com.kardapio.kardapioapi.exceptions.database.RecordConflictException;
import com.kardapio.kardapioapi.exceptions.database.RecordInvalidException;
import com.kardapio.kardapioapi.exceptions.database.RecordNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProfileService {

    private static final String ERROR_MESSAGE = "Não existe perfil para o usuário autenticado.";
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository,
                          ProfileMapper profileMapper,
                          UserService userService,
                          UserRepository userRepository){
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.profileMapper = profileMapper;
        this.userService = userService;
    }

    public Long getProfileId () {
        return Optional.ofNullable(this.userService.getUserByAuth())
                .map(UserModel::getProfileModel)
                .map(ProfileModel::getId)
                .orElseThrow(() -> new RecordNotFoundException(ERROR_MESSAGE));
    }

    public Optional<Long> getOptionalProfileId(UserModel user) {
        return Optional.ofNullable(user)
                .map(UserModel::getProfileModel)
                .map(ProfileModel::getId);
    }

    public ProfileDTO findProfile() {
        return this.profileRepository.findById(this.getProfileId())
                .map(this.profileMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(ERROR_MESSAGE));
    }

    @Transactional
    public ProfileDTO createProfile(@Valid ProfileDTO profileDTO) {
        UserModel user = this.userService.getUserByAuth();
        if (this.getOptionalProfileId(user).isPresent()) {
            throw new RecordConflictException("Este usuário já possui um perfil.");
        }
        ProfileModel newProfile = this.profileMapper.toModel(profileDTO, user.getId());
        user.setProfileModel(newProfile);
        this.userRepository.save(user);
        return this.profileMapper.toDTO(newProfile);
    }

    @Transactional
    public ProfileDTO updateProfile(@Valid @NotNull ProfileDTO profileDTO) {
        ProfileModel existingProfile = Optional.ofNullable(this.getProfileId())
                .flatMap(this.profileRepository::findById)
                .orElseThrow(() -> new RecordNotFoundException(ERROR_MESSAGE));

        validateProfileUpdates(profileDTO, existingProfile);

        BeanUtils.copyProperties(profileDTO, existingProfile);

        this.profileRepository.save(existingProfile);
        return this.profileMapper.toDTO(existingProfile);
    }

    private void validateProfileUpdates(ProfileDTO profileDTO, ProfileModel existingProfile) {
        if (!profileDTO.cpf().equals(existingProfile.getCpf())) {
            throw new RecordInvalidException("O CPF não pode ser alterado.");
        }
        if (profileDTO.equals(this.profileMapper.toDTO(existingProfile))) {
            throw new RecordInvalidException("Não há dados para serem alterados.");
        }
    }
}
