package com.prices.exception.error.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prices.exception.error.ErrorCode;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDto {
    @JsonProperty(value = "date")
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private LocalDateTime date;

    @JsonProperty(value = "type")
    private ErrorCode type;

    @JsonProperty(value = "code")
    private Integer errorCode;

    @JsonProperty(value = "message")
    private String message;

}
