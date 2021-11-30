package com.example.Proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoDto {
    private Integer numeroPrestamo;
    private Float saldoDeuda;
    private String numeroProximaCuota;
    private float valorProximaCuota;
}
