package com.kardapio.kardapioapi.domain.profile.dto;

import com.kardapio.kardapioapi.domain.address.dto.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

public record ProfileDTO(
        @NotEmpty
        @Size(min = 3, max = 25)
        String name,
        @NotEmpty
        @Size(min = 3, max = 45)
        String lastName,
        @CPF
        @Size(min = 11, max = 11)
        String cpf,
        @NotEmpty
        @Pattern(regexp = "^\\+55\\d{11}$", message = "Número de telefone inválido.")
        @Size(min = 14, max = 14)
        String phone,
        @NotEmpty
        @Pattern(regexp = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$",
                message = "URL inválida.")
        String picture,

        @Valid
        Set<AddressDTO> address) {
}
