package br.com.challenge.newssenderapi.config.exception;

import lombok.Data;

@Data
public class ExceptionDetails {

    private Integer status;

    private String message;
}
