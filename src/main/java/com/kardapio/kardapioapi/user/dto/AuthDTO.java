package com.kardapio.kardapioapi.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthDTO(@NotBlank @NotNull String email,
                      @NotBlank @NotNull String password) {
}
