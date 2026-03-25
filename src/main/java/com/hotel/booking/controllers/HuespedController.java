package com.hotel.booking.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.web.bind.annotation.RestController;

import com.hotel.booking.models.Huesped;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class HuespedController {

    private List<Huesped> huespedes = new ArrayList<>();

    public HuespedController() {
        huespedes.add(new Huesped(1, "12345678", "Juan", "Pérez", "juan.perez@example.com", "1234567890", "123 Main St", "Roma", "Italia"));
        huespedes.add(new Huesped(2, "87654321", "María", "Gómez", "maria.gomez@example.com", "0987654321", "456 Elm St", "Santiago", "Chile"));
        huespedes.add(new Huesped(3, "11223344", "Carlos", "López", "carlos.lopez@example.com", "1122334455", "789 Oak St", "Buenos Aires", "Argentina"));
        huespedes.add(new Huesped(4, "44332211", "Ana", "Martínez", "ana.martinez@example.com", "6677889900", "321 Pine St", "Lima", "Peru"));
        huespedes.add(new Huesped(5, "55667788", "Luis", "Rodríguez", "luis.rodriguez@example.com", "5566778899", "654 Maple St", "Bogota", "Colombia"));
        huespedes.add(new Huesped(6, "99887766", "Sofía", "García", "sofia.garcia@example.com", "9988776655", "987 Cedar St", "Montevideo", "Uruguay"));
        huespedes.add(new Huesped(7, "66778899", "Miguel", "Fernández", "miguel.fernandez@example.com", "6677889900", "321 Birch St", "Quito", "Ecuador"));
        huespedes.add(new Huesped(8, "33445566", "Laura", "Sánchez", "laura.sanchez@example.com", "3344556677", "654 Spruce St", "Lima", "Peru"));
        huespedes.add(new Huesped(9, "77889900", "Diego", "Ramírez", "diego.ramirez@example.com", "7788990011", "987 Willow St", "Bogota", "Colombia"));
        huespedes.add(new Huesped(10, "55664433", "Valentina", "Torres", "valentina.torres@example.com", "5566443344", "123 Oak St", "Santiago", "Chile"));
    }

    // Método para ver todos los huéspedes existentes.

    @GetMapping("/huespedes")
    public List<Huesped> getHuespedes() {
        return huespedes;
    }

    // Método para ver un huésped específico por su ID.

    @GetMapping("/huespedes/{id}")
    public Huesped getHuespedById(@PathVariable int id) {
        for (Huesped huesped : huespedes) {
            if (huesped.getId() == id) {
                return huesped;
            }
        }
        return null;
    }

}