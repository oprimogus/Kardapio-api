package com.kardapio.kardapioapi.domain.restaurant.service;

import com.kardapio.kardapioapi.api.clients.cep.CepDTO;
import com.kardapio.kardapioapi.api.clients.cep.ViaCepClient;
import com.kardapio.kardapioapi.domain.restaurant.dto.RestaurantDTO;
import com.kardapio.kardapioapi.domain.restaurant.dto.RestaurantMapper;
import com.kardapio.kardapioapi.domain.restaurant.model.RestaurantModel;
import com.kardapio.kardapioapi.domain.restaurant.repository.RestaurantRepository;
import com.kardapio.kardapioapi.exceptions.apis.ViaCEPException;
import com.kardapio.kardapioapi.exceptions.database.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantMapper restaurantMapper;

    private final ViaCepClient viaCepClient;

    public RestaurantService (RestaurantRepository restaurantRepository,
                              RestaurantMapper restaurantMapper,
                              ViaCepClient viaCepClient) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.viaCepClient = viaCepClient;
    }

    public List<RestaurantDTO> findRestaurants(String city) {
        Optional<List<RestaurantModel>> restaurants = this.restaurantRepository.findAllByAddressModel_City(city);
        return restaurants.map(restaurantModels -> restaurantModels.stream()
                .map(restaurantMapper::toDTO)
                .toList()).orElse(Collections.emptyList());
    }

    public RestaurantDTO findRestaurant(String slug) {
        Optional<RestaurantModel> restaurantModel = this.restaurantRepository.findBySlug(slug);
        if (restaurantModel.isEmpty()) throw new RecordNotFoundException("Restaurante não encontrado.");
        return this.restaurantMapper.toDTO(restaurantModel.get());
    }

    public CepDTO findAddress(String cep) throws ViaCEPException {
        return this.viaCepClient.findCep(cep);

    }
}
