package com.example.Proyecto.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaldoException extends Exception{
    private String body;

    public SaldoException(String message){
        this.body = message;
    }

    public SaldoException(String message, String body) {
        super(message);
        this.body = body;
    }
}

