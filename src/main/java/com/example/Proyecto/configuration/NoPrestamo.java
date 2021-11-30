package com.example.Proyecto.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoPrestamo extends Exception{
    private String body;

    public NoPrestamo(String message){
        this.body = message;
    }

    public NoPrestamo(String message, String body) {
        super(message);
        this.body = body;
    }
}

