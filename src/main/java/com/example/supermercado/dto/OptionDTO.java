package com.example.supermercado.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** DTO para opciones de un select dinámico (tutorial del profesor) */
@Getter
@AllArgsConstructor
public class OptionDTO {
    private final String value;
    private final String label;
}
