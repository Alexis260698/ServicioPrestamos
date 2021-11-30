package com.example.Proyecto.controller;

import com.example.Proyecto.configuration.*;
import com.example.Proyecto.dto.PrestamoDto;
import com.example.Proyecto.entity.Cliente;
import com.example.Proyecto.entity.Cuenta;
import com.example.Proyecto.entity.Cuota;
import com.example.Proyecto.entity.Prestamo;
import com.example.Proyecto.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class PrestamoController {
    @Autowired
    private PrestamoRepository prestamoRepository;


    @Autowired
    RestTemplate restTemplate;

    private Optional<Prestamo> cuentas_id;
    private List<Cuenta> cuenta;
    private Integer coutas_id;
    private float saldo;


    @PostMapping("/altaprestamo/{usuario}/{cuentaAcreditacion}/{cuentaDebito}")
    public ResponseEntity<Prestamo> realizarPrestamo(@RequestBody Prestamo prestamo,
                                                     @PathVariable("usuario") String usuario,
                                                     @PathVariable("cuentaAcreditacion") String cuentaAcreditacion,
                                                     @PathVariable("cuentaDebito") String cuentaDebito) {

        if (!islogged(usuario)) {
            return new ResponseEntity("Primero debes Iniciar sesion", HttpStatus.BAD_REQUEST);
        }

        Cliente cliente = prestamoRepository.consumirCliente(usuario);
        if (cliente.getUsuario() != null) {
            if (buscarCuenta(cliente, cuentaAcreditacion)) {

                Optional<Cuenta> cuentaDeAcreditacion = prestamoRepository.traerCuenta(cliente, cuentaAcreditacion);
                if (cuentaDeAcreditacion.isPresent()) {
                    return ResponseEntity.ok(prestamoRepository.crearPrestamo(cliente, cuentaAcreditacion, cuentaDebito, prestamo));
                }

            }
            return new ResponseEntity("Peticion fallida", HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity("El usuario no se encontro", HttpStatus.NOT_FOUND);
    }


    @GetMapping("/listarPrestamos/{usuario}")
    public ResponseEntity<List<PrestamoDto>> listarPrestamos(@PathVariable("usuario") String usuario) {
        if (!islogged(usuario)) {
            return new ResponseEntity("Primero debes Iniciar sesion", HttpStatus.BAD_REQUEST);
        }

        Cliente cliente = prestamoRepository.consumirCliente(usuario);

        if (cliente.getUsuario() != null) {
            return ResponseEntity.ok(prestamoRepository.obtenerPrestamos(cliente));
        }

        return new ResponseEntity("Cliente no encontrado", HttpStatus.NOT_FOUND);

    }


    public boolean islogged(String usuario) {
        Boolean isLogged = restTemplate.getForObject("http://localhost:8080/islogged/" + usuario, Boolean.class);
        System.out.println(isLogged.booleanValue());
        return isLogged;
    }

    public boolean buscarCuenta(Cliente cliente, String cuentaAcreditacion) {
        for (Cuenta c : cliente.getCuentas()) {
            if (c.getCbu().equals(cuentaAcreditacion)) {
                if (c.getSaldo() > 20000) {
                    return true;
                }
            }
        }
        return false;
    }


}


