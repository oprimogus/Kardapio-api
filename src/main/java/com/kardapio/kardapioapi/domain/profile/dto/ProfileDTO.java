package com.kardapio.kardapioapi.domain.profile.dto;

import com.kardapio.kardapioapi.domain.address.dto.AddressDTO;

import java.util.Set;

public record ProfileDTO(String name,
                         String lastName,
                         String cpf,
                         String phone,
                         String picture,
                         Set<AddressDTO> address) {
}
