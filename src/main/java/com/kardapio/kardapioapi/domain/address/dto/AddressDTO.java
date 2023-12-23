package com.kardapio.kardapioapi.domain.address.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressDTO(
        @NotEmpty
        @Size(min = 8, max = 60)
        String street,
        @NotEmpty
        @Pattern(regexp = "^(S/N|\\d{1,5}[A-Za-z]?(-\\d{1,5}[A-Za-z]?)?)$", message = "Número residencial inválido")
        String number,
        @Size(max = 15)
        String complement,
        @NotEmpty
        @Size(max = 45)
        String district,
        @NotEmpty
        @Size(max = 30)
        String city,
        @NotEmpty
        @Size(max = 30)
        String state,
        @NotEmpty
        @Size(max = 10)
        @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP Inválido.")
        String zip) {
}
