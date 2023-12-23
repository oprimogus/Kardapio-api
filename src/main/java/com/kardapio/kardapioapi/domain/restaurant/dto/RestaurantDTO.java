package com.kardapio.kardapioapi.domain.restaurant.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RestaurantDTO(
        @NotEmpty
        @Size(max = 45)
        String name,
        @NotEmpty
        @Size(max = 25)
        String slug,
        @NotEmpty
        @Size(max = 3)
        Float score
) {}
