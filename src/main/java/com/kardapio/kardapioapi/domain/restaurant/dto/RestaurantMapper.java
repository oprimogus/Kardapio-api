package com.kardapio.kardapioapi.domain.restaurant.dto;

import com.kardapio.kardapioapi.domain.address.dto.AddressMapper;
import com.kardapio.kardapioapi.domain.restaurant.model.RestaurantModel;
import com.kardapio.kardapioapi.domain.restaurant.repository.RestaurantRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    private final RestaurantRepository restaurantRepository;

    private final AddressMapper addressMapper;

    public RestaurantMapper(RestaurantRepository restaurantRepository,
                            AddressMapper addressMapper) {
        this.restaurantRepository = restaurantRepository;
        this.addressMapper = addressMapper;
    }

//    public RestaurantModel toModel(@Valid RestaurantRegisterDTO restaurantDTO) {
//        Optional<RestaurantModel> restaurantModel = this.restaurantRepository.findByNameAndCnpj(restaurantDTO);
//        if (restaurantModel.isPresent()) throw new RecordConflictException("Restaurante já cadastrado.");
//        var newRestauranteModel = new RestaurantModel();
//        newRestauranteModel.setName(restaurantDTO.name());
//        newRestauranteModel.setSlug();
//        newRestauranteModel.setCnpj(restaurantDTO.cnpj());
//        newRestauranteModel.setPhone(restaurantDTO.phone());
//        newRestauranteModel.setScore(5);
////        newRestauranteModel.setAddressModel(addressMapper.toModel(restaurantDTO.addressDTO()));
//        return newRestauranteModel;
//    }

    public RestaurantDTO toDTO(@Valid RestaurantModel restaurantModel) {
        return new RestaurantDTO(
                restaurantModel.getName(),
                restaurantModel.getSlug(),
                restaurantModel.getScore()
//                restaurantModel.getAddressModel().getZip(),
//                restaurantModel.getAddressModel().getDistrict()
        );
    }
}
