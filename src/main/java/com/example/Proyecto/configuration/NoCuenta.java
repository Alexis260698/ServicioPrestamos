package com.example.Proyecto.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoCuenta extends Exception{
    private String body;

    public NoCuenta(String message){
        this.body = message;
    }

    public NoCuenta(String message, String body) {
        super(message);
        this.body = body;
    }
}

