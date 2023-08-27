package com.kardapio.kardapioapi.domain.profile.dto;

import com.kardapio.kardapioapi.domain.address.dto.AddressDTO;
import com.kardapio.kardapioapi.domain.address.dto.AddressMapper;
import com.kardapio.kardapioapi.domain.address.model.AddressModel;
import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import com.kardapio.kardapioapi.domain.user.repository.UserRepository;
import com.kardapio.kardapioapi.exceptions.database.RecordNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProfileMapper {

    private final UserRepository userRepository;

    private final AddressMapper addressMapper;

    public ProfileMapper(UserRepository userRepository,
                         AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.addressMapper = addressMapper;
    }

    public ProfileModel toModel (@Valid ProfileDTO profileDTO, UUID userId) {
        Optional<UserModel> userModel = this.userRepository.findById(userId);
        if (userModel.isEmpty()) {
            throw new RecordNotFoundException("Usuário não existe");
        }
        var profileModel = new ProfileModel();
        profileModel.setUserModel(userModel.get());
        profileModel.setName(profileDTO.name());
        profileModel.setLastName(profileDTO.lastName());
        profileModel.setPhone(profileDTO.phone());
        profileModel.setCpf(profileDTO.cpf());
        profileModel.setPicture(profileDTO.picture());
        if (profileDTO.address() != null) {
            Set<AddressModel> address = profileDTO.address().stream()
                    .map(addressItem -> addressMapper.toModel(addressItem, profileModel))
                    .collect(Collectors.toSet());
            profileModel.setAddress(address);
        }
        return profileModel;
    }

    public ProfileDTO toDTO(@Valid ProfileModel profileModel) {
        if (profileModel == null) {
            return null;
        }
        Set<AddressDTO> addressDTOList = null;
        if (profileModel.getAddress() != null) {
            addressDTOList = profileModel.getAddress()
                    .stream()
                    .map(addressMapper::toDTO)
                    .collect(Collectors.toSet());
        }
        return new ProfileDTO(profileModel.getName(),
                profileModel.getLastName(),
                profileModel.getCpf(),
                profileModel.getPhone(),
                profileModel.getPicture(),
                addressDTOList);
    }
}
