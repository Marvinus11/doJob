package com.marvinus.doJob.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ErrorMessageDTO {
    private String message;
    private String field;

    public ErrorMessageDTO(String message, String field) {
        this.message = message;
        this.field = field;
    }
}

