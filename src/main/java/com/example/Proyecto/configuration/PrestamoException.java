package com.example.Proyecto.configuration;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrestamoException extends Exception {
    private String body;

    public PrestamoException(String message){
        this.body = message;
    }

    public PrestamoException(String message, String body) {
        super(message);
        this.body = body;
    }
}
