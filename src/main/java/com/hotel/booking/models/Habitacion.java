package com.hotel.booking.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Habitacion {

    private int id;
    private String numero_habitacion;
    private String tipo_habitacion;
    private String descripcion;
    private int capacidad;
    private double precio_por_noche;
    private boolean disponibilidad;

}
