package com.project.momo.dto.error;

public class PropertyBindingExceptionResponseDto extends JsonProcessingExceptionResponseDto {

    private String unrecognizedField;

    public PropertyBindingExceptionResponseDto(String unrecognizedField) {
        super();
        this.unrecognizedField = unrecognizedField;
    }
}