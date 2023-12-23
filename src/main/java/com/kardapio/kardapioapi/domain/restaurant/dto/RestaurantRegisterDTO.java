package com.kardapio.kardapioapi.domain.restaurant.dto;

import com.kardapio.kardapioapi.domain.address.dto.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record RestaurantRegisterDTO(
        @NotEmpty
        @Size(max = 45)
        String name,
        @NotEmpty
        @CNPJ
        @Size(min = 14, max = 14 )
        String cnpj,
        @NotEmpty
        @Size(max = 3)
        String phone,
        @NotEmpty
        @Valid
        AddressDTO addressDTO
) {}
