package com.example.Proyecto.configuration;

import com.example.Proyecto.entity.Prestamo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ SaldoException.class })
    protected ResponseEntity<Object> saldo(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "No tienes saldo suficiente para hacer un prestamo",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @ExceptionHandler({ PrestamoException.class })
    protected ResponseEntity<Object> fallido(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Operacion Fallida",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }
    @ExceptionHandler({ NoCuenta.class })
    protected ResponseEntity<Object> sinCuenta(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "La cuenta no existe o esta bloqueada, favor de contactar asistencia a clientes para mas informacion.",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }
    @ExceptionHandler({ NoPrestamo.class })
    protected ResponseEntity<Object> sinPrestamo(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "No tienes ningun prestamo, Lo sentimos.",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }


}
