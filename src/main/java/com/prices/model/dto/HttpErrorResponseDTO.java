package com.prices.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpErrorResponseDTO {
    private int errorCode;
    private String errorMessage;
}
