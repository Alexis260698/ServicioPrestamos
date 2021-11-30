package com.example.Proyecto.repository;

import com.example.Proyecto.entity.Prestamo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface PrestamoRepositoryDAO  extends CrudRepository<Prestamo, Integer> {

    @Query(value = "SELECT p FROM prestamo_cuentas p WHERE p.cuentas_ide=:cuentas_id", nativeQuery = true)
    Optional<Prestamo> getPrestamos(Integer cuentas_id);

    @Query(value = "SELECT p FROM prestamo_cuotas p WHERE p.cuotas_ide=:cuotas_id", nativeQuery = true)
    Optional<Prestamo> getCuotas(Integer cuotas_id);
}
