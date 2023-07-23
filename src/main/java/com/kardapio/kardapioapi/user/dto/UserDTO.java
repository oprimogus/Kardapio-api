package com.kardapio.kardapioapi.user.dto;

import com.kardapio.kardapioapi.user.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(@NotBlank @NotNull String token,
                      @NotBlank @NotNull UserRole role) {
}
