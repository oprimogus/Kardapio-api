package com.kardapio.kardapioapi.exceptions.dto;

import java.time.Instant;
import java.util.List;

public record ErrorDTO(Instant timestamp, String message, String details, List<FieldDTO> fields) {
}
