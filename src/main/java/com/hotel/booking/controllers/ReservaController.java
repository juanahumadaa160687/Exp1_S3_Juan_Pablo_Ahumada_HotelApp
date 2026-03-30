package com.hotel.booking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.booking.models.Habitacion;
import com.hotel.booking.models.Huesped;
import com.hotel.booking.models.Reserva;

@RestController
public class ReservaController {

    private List<Reserva> reservas = new ArrayList<>();

    public ReservaController() {

        reservas.add(new Reserva(1, 1, 1, 2, 0, "2024-07-01", "2024-07-05", 400.0, "Ninguna"));
        reservas.add(new Reserva(2, 2, 2, 2, 1, "2024-07-10", "2024-07-15", 600.0, "Cama extra para niño"));
        reservas.add(new Reserva(3, 3, 3, 4, 0, "2024-08-01", "2024-08-10", 1500.0, "Ninguna"));
        reservas.add(new Reserva(4, 4, 4, 5, 2, "2024-08-15", "2024-08-20", 1200.0, "Cancelada por el cliente"));
        reservas.add(new Reserva(5, 5, 5, 1, 0, "2024-09-01", "2024-09-05", 250.0, "Ninguna"));
        reservas.add(new Reserva(6, 6, 6, 2, 1, "2024-09-10", "2024-09-15", 400.0, "Cama extra para niño"));
        reservas.add(new Reserva(7, 7, 7, 4, 0, "2024-10-01", "2024-10-10", 1500.0, "Ninguna"));
        reservas.add(new Reserva(8, 8, 8, 5, 2, "2024-10-15", "2024-10-20", 1200.0, "Cancelada por el cliente"));
        reservas.add(new Reserva(9, 9, 9, 1, 0, "2024-11-01", "2024-11-05", 250.0, "Ninguna"));
        reservas.add(new Reserva(10, 10, 10, 2, 1, "2024-11-10", "2024-11-15", 400.0, "Cama extra para niño"));

    }

    // Método para ver todas las reservas existentes.

    @GetMapping("/reservas")
    public List<Reserva> getReservas() {
        return reservas;
    }

    // Método para ver una reserva específica por su ID.

    @GetMapping("/reservas/{id}")
    public Reserva getReservaById(@PathVariable int id) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == id) {
                return reserva;
            }
        }
        return null;
    }

    // Método para ver las reservas de un huésped específico por su ID.

    @GetMapping("/reservas/huesped/{id_huesped}")
    public Reserva getReservasByHuespedId(@PathVariable int id_huesped) {

        for (Reserva reserva : reservas) {  
            if (reserva.getId_huesped() == id_huesped) {
                return reserva;
            }
        }
        return null;

    }

    // Método para ver las reservas de una habitación específica por su ID.

    @GetMapping("/reservas/habitacion/{id_habitacion}")
    public Reserva getReservasByHabitacionId(@PathVariable int id_habitacion) {

        for (Reserva reserva : reservas) {  
            if (reserva.getId_habitacion() == id_habitacion) {
                return reserva;
            }
        }
        return null;

    }

    // Método para ver disponibilidad de habitaciones en una fecha específica.

    @GetMapping("/reservas/disponibilidad/{fecha}")
    public List<Habitacion> getReservasByFecha(@PathVariable String fecha) {

        List<Reserva> reservasEnFecha = new ArrayList<>();
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();


        String[] partesFecha = fecha.split("-");
        int año = Integer.parseInt(partesFecha[0]);
        int mes = Integer.parseInt(partesFecha[1]);
        int dia = Integer.parseInt(partesFecha[2]);

        for (Reserva reserva : reservas) {  
            String[] partesFechaCheckIn = reserva.getFecha_check_in().split("-");
            int añoCheckIn = Integer.parseInt(partesFechaCheckIn[0]);
            int mesCheckIn = Integer.parseInt(partesFechaCheckIn[1]);
            int diaCheckIn = Integer.parseInt(partesFechaCheckIn[2]);

            String[] partesFechaCheckOut = reserva.getFecha_check_out().split("-");
            int añoCheckOut = Integer.parseInt(partesFechaCheckOut[0]);
            int mesCheckOut = Integer.parseInt(partesFechaCheckOut[1]);
            int diaCheckOut = Integer.parseInt(partesFechaCheckOut[2]);

            if ((año > añoCheckIn || (año == añoCheckIn && mes > mesCheckIn) || (año == añoCheckIn && mes == mesCheckIn && dia >= diaCheckIn)) &&
                (año < añoCheckOut || (año == añoCheckOut && mes < mesCheckOut) || (año == añoCheckOut && mes == mesCheckOut && dia <= diaCheckOut))) {
                
                reservasEnFecha.add(reserva);

                System.out.println(reserva.getFecha_check_in() + " - " + reserva.getId_habitacion());

                for (Reserva r : reservasEnFecha) {
                    HabitacionController habController = new HabitacionController();
                    for (Habitacion habitacion : habController.getHabitaciones()) {
                        if (habitacion.getId() != r.getId_habitacion()) {
                            habitacionesDisponibles.add(habitacion);
                        }
                    }
                }
            }
        }
        return habitacionesDisponibles;
    }

    // Método para cancelar una reserva por su ID.

    @GetMapping("/reservas/cancelar/{id}")
    public String cancelarReserva(@PathVariable int id) {
        
        for (Reserva reserva : reservas) {
            if (reserva.getId() == id) {
                reservas.remove(reserva);

                return "Reserva " + id + " cancelada correctamente";
            }
        }
        return "Reserva no encontrada";
    }

    // Método para ver los detalles de una reserva específica por su ID, incluyendo información del huésped y la habitación.

    @GetMapping("/reservas/detalles/{id}")
    public Object[] detalleReserva(@PathVariable int id) {

        HabitacionController habController = new HabitacionController();
        HuespedController huespedController = new HuespedController();

        for (Reserva reserva : reservas) {

            if (reserva.getId() == id) {

                for (Habitacion habitacion : habController.getHabitaciones()) {
                    if (habitacion.getId() == reserva.getId_habitacion()) {
                        
                        for (Huesped huesped : huespedController.getHuespedes()) {
                            if (huesped.getId() == reserva.getId_huesped()) {
  
                                return new Object[] {reserva, habitacion, huesped};   
                            }
                        }
                    }
                }
                return null;
            }
        }
        return null;
    }

    // Método para crear una nueva reserva.

    @GetMapping("/reservas/nueva")
    public Reserva crearReserva(@RequestParam int id_huesped, @RequestParam int id_habitacion, @RequestParam int numero_adultos, @RequestParam int numero_ninos, @RequestParam String fecha_check_in, @RequestParam String fecha_check_out, @RequestParam String solicitudes_especiales) {

        int nuevoId = reservas.size() + 1;

        HabitacionController habController = new HabitacionController();
        
        double precioHabitacion = 0;

        for (Habitacion habitacion : habController.getHabitaciones()) {
            if (habitacion.getId() == id_habitacion) {
                precioHabitacion = habitacion.getPrecio_por_noche();
            }
        }

        int diasReserva = (Integer.parseInt(fecha_check_out.split("-")[2]) - Integer.parseInt(fecha_check_in.split("-")[2]));
        
        if (diasReserva > 0) {
            double precioTotalCalculado = precioHabitacion * diasReserva;

            Reserva nuevaReserva = new Reserva(nuevoId, id_huesped, id_habitacion, numero_adultos, numero_ninos, fecha_check_in, fecha_check_out, precioTotalCalculado, solicitudes_especiales);
        
            reservas.add(nuevaReserva);
        
            return nuevaReserva;
        }
        else {
            double precioTotalCalculado = precioHabitacion * diasReserva * -1;

            Reserva nuevaReserva = new Reserva(nuevoId, id_huesped, id_habitacion, numero_adultos, numero_ninos, fecha_check_in, fecha_check_out, precioTotalCalculado, solicitudes_especiales);
        
            reservas.add(nuevaReserva);
        
            return nuevaReserva;
        }
    }
}