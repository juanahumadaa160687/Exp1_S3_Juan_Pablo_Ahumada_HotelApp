package com.hotel.booking.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class Reserva {

    private int id;
    private int id_huesped;
    private int id_habitacion;
    private int numero_adultos;
    private int numero_ninos;
    private String fecha_check_in;
    private String fecha_check_out;
    private double precio_total;
    private String solicitudes_especiales;

}
