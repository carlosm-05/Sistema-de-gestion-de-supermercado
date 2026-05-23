package com.example.supermercado.dto;

import lombok.Getter;
import java.util.List;

/** DTO auxiliar para campos del formulario dinámico (tutorial del profesor) */
@Getter
public class FormFieldDTO {
    private final String type;
    private final String name;
    private final String label;
    private final String value;
    private final boolean required;
    private final List<OptionDTO> options;
    private final String extraAttrs;

    public FormFieldDTO(String type, String name, String label, String value, boolean required) {
        this(type, name, label, value, required, null, null);
    }

    public FormFieldDTO(String type, String name, String label, String value,
            boolean required, List<OptionDTO> options) {
        this(type, name, label, value, required, options, null);
    }

    public FormFieldDTO(String type, String name, String label, String value,
            boolean required, List<OptionDTO> options, String extraAttrs) {
        this.type = type;
        this.name = name;
        this.label = label;
        this.value = value != null ? value : "";
        this.required = required;
        this.options = options;
        this.extraAttrs = extraAttrs != null ? extraAttrs : "";
    }
}
