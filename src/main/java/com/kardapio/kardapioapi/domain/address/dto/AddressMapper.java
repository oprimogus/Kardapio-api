package com.kardapio.kardapioapi.domain.address.dto;

import com.kardapio.kardapioapi.domain.address.model.AddressModel;
import com.kardapio.kardapioapi.domain.profile.model.ProfileModel;
import com.kardapio.kardapioapi.domain.restaurant.model.RestaurantModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressModel toModel(@Valid AddressDTO addressDTO) {
        AddressModel addressModel = new AddressModel();
        addressModel.setStreet(addressDTO.street());
        addressModel.setNumber(addressDTO.number());
        addressModel.setComplement(addressDTO.complement());
        addressModel.setDistrict(addressDTO.district());
        addressModel.setCity(addressDTO.city());
        addressModel.setState(addressDTO.state());
        addressModel.setZip(addressDTO.zip());
        return addressModel;
    }

    public AddressModel toModel(@Valid AddressDTO addressDTO, ProfileModel profileModel) {
        AddressModel addressModel = new AddressModel();
        addressModel.setStreet(addressDTO.street());
        addressModel.setNumber(addressDTO.number());
        addressModel.setComplement(addressDTO.complement());
        addressModel.setDistrict(addressDTO.district());
        addressModel.setCity(addressDTO.city());
        addressModel.setState(addressDTO.state());
        addressModel.setZip(addressDTO.zip());
        addressModel.setProfileModel(profileModel);
        return addressModel;
    }

    public AddressModel toModel(@Valid AddressDTO addressDTO, RestaurantModel restaurantModel) {
        AddressModel addressModel = new AddressModel();
        addressModel.setStreet(addressDTO.street());
        addressModel.setNumber(addressDTO.number());
        addressModel.setComplement(addressDTO.complement());
        addressModel.setDistrict(addressDTO.district());
        addressModel.setCity(addressDTO.city());
        addressModel.setState(addressDTO.state());
        addressModel.setZip(addressDTO.zip());
        addressModel.setRestaurantModel(restaurantModel);
        return addressModel;
    }

    public AddressDTO toDTO(@Valid AddressModel addressModel) {
        return new AddressDTO(
                addressModel.getStreet(),
                addressModel.getNumber(),
                addressModel.getComplement(),
                addressModel.getDistrict(),
                addressModel.getCity(),
                addressModel.getState(),
                addressModel.getZip());
    }
}
