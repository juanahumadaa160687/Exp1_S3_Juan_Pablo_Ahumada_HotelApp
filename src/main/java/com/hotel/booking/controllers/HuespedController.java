package com.hotel.booking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.hotel.booking.models.Huesped;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class HuespedController {

    private List<Huesped> huespedes = new ArrayList<>();

    public HuespedController() {
        huespedes.add(new Huesped(1, "Juan", "Perez", "juan.perez@example.com", "1234567890", "123 Main St", "Roma", "Italia"));
        huespedes.add(new Huesped(2, "Maria", "Gomez", "maria.gomez@example.com", "0987654321", "456 Elm St", "Santiago", "Chile"));
        huespedes.add(new Huesped(3, "Carlos", "Lopez", "carlos.lopez@example.com", "1122334455", "789 Oak St", "Buenos Aires", "Argentina"));
        huespedes.add(new Huesped(4, "Ana", "Martinez", "ana.martinez@example.com", "6677889900", "321 Pine St", "Lima", "Peru"));
        huespedes.add(new Huesped(5, "Luis", "Garcia", "luis.garcia@example.com", "5566778899", "654 Maple St", "Bogota", "Colombia"));
        huespedes.add(new Huesped(6, "Sofia", "Rodriguez", "sofia.rodriguez@example.com", "4433221100", "987 Cedar St", "Montevideo", "Uruguay"));
        huespedes.add(new Huesped(7, "Diego", "Fernandez", "diego.fernandez@example.com", "3344556677", "123 Birch St", "Quito", "Ecuador"));
        huespedes.add(new Huesped(8, "Laura", "Sanchez", "laura.sanchez@example.com", "2233445566", "456 Spruce St", "Bogota", "Colombia"));
        huespedes.add(new Huesped(9, "Jorge", "Ramirez", "jorge.ramirez@example.com", "9988776655", "789 Willow St", "Lima", "Peru"));
        huespedes.add(new Huesped(10, "Isabella", "Vargas", "isabella.vargas@example.com", "1122446688", "321 Oak St", "Santiago", "Chile"));
    }

    @GetMapping("/huespedes")
    public List<Huesped> getHuespedes() {
        return huespedes;
    }

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