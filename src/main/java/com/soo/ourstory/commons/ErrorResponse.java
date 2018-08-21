package com.soo.ourstory.commons;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ErrorResponse {

    private String message;

    private String code;

    private List<FieldError> errors;

    public static class FieldError{
        private String field;
        private String value;
        private String reason;
    }

}
