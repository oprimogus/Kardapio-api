package com.kardapio.kardapioapi.exceptions.dto;

import java.time.Instant;

public record ErrorDTO(Instant timestamp, String message, String details) {
}
