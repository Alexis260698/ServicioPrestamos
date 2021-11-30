package com.example.Proyecto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Cuota")
@Getter
@Setter
@NoArgsConstructor
public class Cuota {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;

    private Float valorCuota;
    private String proximaCuota;
    private boolean pagada;

    public Cuota(String id,Float valorCuota, String proximaCuota, boolean pagada) {
        this.id=id;
        this.valorCuota = valorCuota;
        this.proximaCuota = proximaCuota;
        this.pagada = pagada;
    }

    @Override
    public String toString() {
        return "Cuota{" +
                "valorCuota=" + valorCuota +
                ", proximaCuota='" + proximaCuota + '\'' +
                ", pagada=" + pagada +
                '}';
    }
}
