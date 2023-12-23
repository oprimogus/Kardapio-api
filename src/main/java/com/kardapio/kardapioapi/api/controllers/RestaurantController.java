package com.kardapio.kardapioapi.api.controllers;

import com.kardapio.kardapioapi.api.clients.cep.CepDTO;
import com.kardapio.kardapioapi.domain.restaurant.dto.RestaurantDTO;
import com.kardapio.kardapioapi.domain.restaurant.service.RestaurantService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<RestaurantDTO> findRestaurants (@RequestParam String city) {
        return this.restaurantService.findRestaurants(city);
    }

    @GetMapping("/cep/{cep}")
    @PreAuthorize("permitAll()")
    public CepDTO findCep (@PathVariable String cep) {
        return this.restaurantService.findAddress(cep);
    }
}
