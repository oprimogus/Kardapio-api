package com.kardapio.kardapioapi.domain.restaurant.repository;

import com.kardapio.kardapioapi.domain.restaurant.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantModel, Long> {
    Optional<List<RestaurantModel>> findAllByAddressModel_City(String city);

    Optional<RestaurantModel> findBySlug(String slug);
}
