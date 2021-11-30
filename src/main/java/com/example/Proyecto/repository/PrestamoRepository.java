package com.example.Proyecto.repository;

import com.example.Proyecto.dto.PrestamoDto;
import com.example.Proyecto.entity.Cliente;
import com.example.Proyecto.entity.Cuenta;
import com.example.Proyecto.entity.Cuota;
import com.example.Proyecto.entity.Prestamo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PrestamoRepository {

    @Autowired
    private PrestamoRepositoryDAO prestamoDAO;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ModelMapper modelMapper;



    public Optional<Prestamo> getP(Integer cuentas_id) {
        return prestamoDAO.getPrestamos(cuentas_id);
    }

    public boolean crearP(Prestamo prestamo) {
        try {
            prestamoDAO.save(prestamo);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public float sacarSaldo(List<Cuenta> cuenta) {
        for (int c = 0; c < cuenta.size(); c++) {
            if (cuenta.get(c).getSaldo() > 20.000) {
                return cuenta.get(c).getSaldo();
            }
            return cuenta.get(c).getSaldo();
        }
        return 0.0F;
    }

    public Optional<Prestamo> getC(Integer cuotas_id) {
        return prestamoDAO.getCuotas(cuotas_id);
}

    public Prestamo crearPrestamo(Cliente cliente, String cuentaAcreditacion, String cuentaDebito, Prestamo prestamo) {
        if(hacerdeposito(cliente, cuentaDebito, prestamo.getImporte())){
            prestamo.setSaldoDeuda(calcularMonto(prestamo.getImporte(), prestamo.getTasaInteres()));
            prestamo.setCapitalOriginal(prestamo.getImporte());
            prestamo.setFechaDeVencimiento(calcularfecha(365));
            prestamo.setCuotas(Crearcuotas(prestamo.getImporte(),
                    prestamo.getTasaInteres(),cliente.getUsuario().substring(0,2)));
            prestamo.setCuentaAcreditacion(cuentaAcreditacion);
            prestamo.setCuentaDebito(cuentaDebito);
            //falta agregar prestamo a cliente y mandarloa BD
            Cliente clienteConPrestamo=agregarPrestamoCliente(cliente, prestamo);
            enviarDatosCliente(clienteConPrestamo);
            return prestamo;
        }
        return null;
    }
    public Cliente enviarDatosCliente(Cliente cliente) {
        restTemplate.put("http://localhost:8080/actualizarCliente", cliente);
        return cliente;
    }

    public List<PrestamoDto> obtenerPrestamos(Cliente cliente){
        List<PrestamoDto> prestamos= new ArrayList<>();
        for(Prestamo p:cliente.getPrestamos()){
            PrestamoDto prestamoDto= modelMapper.map(p,PrestamoDto.class);
            Cuota cuota=traerProximacuota(p);
            if(cuota.getProximaCuota()!=null){
                prestamoDto.setNumeroProximaCuota(cuota.getId());
                prestamoDto.setValorProximaCuota(cuota.getValorCuota());
                prestamos.add(prestamoDto);
            }
        }
        return prestamos;

    }

    public Cuota traerProximacuota(Prestamo prestamo){
        for(Cuota c: prestamo.getCuotas()){
            if(c.isPagada()==false){
                return c;
            }
        }
        return new Cuota();
    }

    public Cliente agregarPrestamoCliente(Cliente cliente, Prestamo prestamo){
        if(cliente.getPrestamos()!=null){
            cliente.getPrestamos().add(prestamo);
        }else{
            List<Prestamo> listaPrestamos= new ArrayList<>();
            listaPrestamos.add(prestamo);
            cliente.setPrestamos(listaPrestamos);
        }
        return cliente;
    }

    public List<Cuota> Crearcuotas(Float importe, Float tasaDeInteres, String id){

        Float montoTontal=calcularMonto(importe, tasaDeInteres);
        float montoPorCuota=montoTontal/12;

        List<Cuota> cuotas= new ArrayList<>();
        for(int i=0; i<12; i++){
            int random= (int) (Math.random()*1500);
                Cuota cuota= new Cuota(id+i+random,montoPorCuota,"",false);
                cuotas.add(cuota);
        }

        for(int i=0; i<12; i++){
            if(i==11){
                cuotas.get(11).setProximaCuota("");
            }else {
                cuotas.get(i).setProximaCuota(cuotas.get(i+1).getId());
            }
        }
        return cuotas;
    }

    public Float calcularMonto(Float importe, Float interes){
        float cantidad=interes/100;
        cantidad=cantidad*importe;
        cantidad+=importe;
        return cantidad;
    }


    private boolean hacerdeposito(Cliente cliente, String cuentaDebito, float importe) {
        for(Cuenta c: cliente.getCuentas()){
            if(c.getCbu().equals(cuentaDebito)){
                c.setSaldo(c.getSaldo()+importe);
                return true;
            }
        }
        return false;
    }

    public String calcularfecha(int dias) {
        LocalDate fecha = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedLocalDate = fecha.format(formatter);
        return sumarDias(formattedLocalDate + " 15:00:00", dias);
    }

    public static String sumarDias(String fecha, int days) {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm:ss");
        LocalDateTime fecha2 = LocalDateTime.parse(fecha, formateador);
        fecha2 = fecha2.plusDays(days);
        return fecha2.format(formateador).substring(0, 10);
    }

    public Cliente consumirCliente(String usuario) {
        try {
            Cliente cliente = restTemplate.getForObject("http://localhost:8080/buscarCliente/" + usuario, Cliente.class);
            return cliente;
        } catch (Exception e) {
            return new Cliente();
        }
    }

    public Optional<Cuenta> traerCuenta(Cliente cliente, String cuenta) {
        if (cliente.getCuentas() != null) {
            return cliente.getCuentas().stream().filter(c -> c.getCbu().equals(cuenta)).findFirst();
        }
        return null;
    }


}













