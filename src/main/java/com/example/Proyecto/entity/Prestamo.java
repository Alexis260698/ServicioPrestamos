package com.example.Proyecto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Prestamo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Prestamo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer numeroPrestamo;

    private Float importe;
    private Float tasaInteres;
    private Float saldoDeuda;
    private Float capitalOriginal;
    private String FechaDeVencimiento;


    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "Prestamo_Cuotas")
    private List<Cuota> cuotas;

    private String cuentaAcreditacion;
    private String cuentaDebito;

}