package com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private Date date;
    private String message;
    private int code;

    public static ErrorResponse of(String message, int code) {
        return new ErrorResponse(new Date(), message, code);
    }
}
