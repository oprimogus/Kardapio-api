package com.kardapio.kardapioapi.domain.profile.dto;

import com.kardapio.kardapioapi.domain.address.dto.AddressDTO;
import com.kardapio.kardapioapi.domain.address.model.AddressModel;
import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.user.model.UserModel;
import com.kardapio.kardapioapi.domain.user.repository.UserRepository;
import com.kardapio.kardapioapi.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProfileMapper {

    private final UserRepository userRepository;

    public ProfileMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileModel toModel (ProfileDTO profileDTO, UUID userId) {
        Optional<UserModel> userModel = this.userRepository.findById(userId);
        if (userModel.isEmpty()) {
            throw new RecordNotFoundException("User no exists");
        }
        var profileModel = new ProfileModel();
        profileModel.setUserModel(userModel.get());
        profileModel.setName(profileDTO.name());
        profileModel.setLastName(profileDTO.lastName());
        profileModel.setPhone(profileDTO.phone());
        profileModel.setCpf(profileDTO.cpf());
        profileModel.setPicture(profileDTO.picture());
        Set<AddressModel> address = null;
        if (profileDTO.address() != null) {
            address = profileDTO.address().stream()
                    .map(addressDTO -> {
                        var addressModel = new AddressModel();
                        addressModel.setStreet(addressDTO.street());
                        addressModel.setCity(addressDTO.city());
                        addressModel.setState(addressDTO.state());
                        addressModel.setZip(addressDTO.zip());
                        addressModel.setProfile(profileModel);
                        return addressModel;
                    })
                    .collect(Collectors.toSet());
        }
        profileModel.setAddress(address);
        return profileModel;
    }

    public ProfileDTO toDTO(ProfileModel profileModel) {
        if (profileModel == null) {
            return null;
        }
        Set<AddressDTO> addressDTOList = null;
        if (profileModel.getAddress() != null) {
            addressDTOList = profileModel.getAddress()
                    .stream()
                    .map(addressModel -> new AddressDTO(addressModel.getStreet(),
                            addressModel.getCity(), addressModel.getState(), addressModel.getZip()))
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
