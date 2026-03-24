package com.hotel.booking.controllers;

import java.util.ArrayList;
import java.util.List;

import com.hotel.booking.models.Habitacion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HabitacionController {

    private List<Habitacion> habitaciones = new ArrayList<>();

    public HabitacionController() {

        habitaciones.add(new Habitacion(1, "101", "Individual", "Habitación para una persona", 1, 50.0, true));
        habitaciones.add(new Habitacion(2, "102", "Doble", "Habitación para dos personas", 2, 80.0, true));
        habitaciones.add(new Habitacion(3, "201", "Suite", "Habitación de lujo con sala de estar", 4, 150.0, false));
        habitaciones.add(new Habitacion(4, "202", "Familiar", "Habitación para toda la familia", 5, 120.0, true));
        habitaciones.add(new Habitacion(5, "301", "Individual", "Habitación para una persona", 1, 50.0, true));
        habitaciones.add(new Habitacion(6, "302", "Doble", "Habitación para dos personas", 2, 80.0, false));
        habitaciones.add(new Habitacion(7, "401", "Suite", "Habitación de lujo con sala de estar", 4, 150.0, true));
        habitaciones.add(new Habitacion(8, "402", "Familiar", "Habitación para toda la familia", 5, 120.0, true));
        habitaciones.add(new Habitacion(9, "501", "Individual", "Habitación para una persona", 1, 50.0, false));
        habitaciones.add(new Habitacion(10, "502", "Doble", "Habitación para dos personas", 2, 80.0, true));

    }

    @GetMapping("/habitaciones")
    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    @GetMapping("/habitaciones/{id}")
    public Habitacion getHabitacionById(@PathVariable int id) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getId() == id) {
                return habitacion;
            }
        }
        return null;
    }

    @GetMapping("/habitaciones/disponibilidad")
    public List<Habitacion> getDisponibilidad() {

        List<Habitacion> habitacionesDisponibles = new ArrayList<>();

        for (Habitacion habitacion : habitaciones) {
            if (habitacion.isDisponibilidad()) {
                habitacionesDisponibles.add(habitacion);
            }

        }
        return habitacionesDisponibles;
    }
    
}
