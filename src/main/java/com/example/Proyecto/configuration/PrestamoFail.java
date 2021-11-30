package com.example.Proyecto.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrestamoFail extends Exception {
    private String body;

    public PrestamoFail(String message){
        this.body = message;
    }

    public PrestamoFail(String message, String body) {
        super(message);
        this.body = body;
    }
}

