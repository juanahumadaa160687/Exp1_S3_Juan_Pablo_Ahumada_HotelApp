package com.hotel.booking.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.booking.models.Habitacion;
import com.hotel.booking.models.Huesped;
import com.hotel.booking.models.Reserva;

@RestController
public class ReservaController {

    private List<Reserva> reservas = new ArrayList<>();

    public ReservaController() {

        reservas.add(new Reserva(1, 1, 1, 2, 0, "2024-07-01", "2024-07-05", false, 400.0, "Ninguna"));
        reservas.add(new Reserva(2, 2, 2, 2, 1, "2024-07-10", "2024-07-15", false, 600.0, "Cama extra para niño"));
        reservas.add(new Reserva(3, 3, 3, 4, 0, "2024-08-01", "2024-08-10", false, 1500.0, "Ninguna"));
        reservas.add(new Reserva(4, 4, 4, 5, 2, "2024-08-15", "2024-08-20", true, 1200.0, "Cancelada por el cliente"));
        reservas.add(new Reserva(5, 5, 5, 1, 0, "2024-09-01", "2024-09-05", false, 250.0, "Ninguna"));
        reservas.add(new Reserva(6, 6, 6, 2, 1, "2024-09-10", "2024-09-15", false, 400.0, "Cama extra para niño"));
        reservas.add(new Reserva(7, 7, 7, 4, 0, "2024-10-01", "2024-10-10", false, 1500.0, "Ninguna"));
        reservas.add(new Reserva(8, 8, 8, 5, 2, "2024-10-15", "2024-10-20", true, 1200.0, "Cancelada por el cliente"));
        reservas.add(new Reserva(9, 9, 9, 1, 0, "2024-11-01", "2024-11-05", false, 250.0, "Ninguna"));
        reservas.add(new Reserva(10, 10, 10, 2, 1, "2024-11-10", "2024-11-15", false, 400.0, "Cama extra para niño"));

    }

    @GetMapping("/reservas")
    public List<Reserva> getReservas() {
        return reservas;
    }

    @GetMapping("/reservas/{id}")
    public Reserva getReservaById(@PathVariable int id) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == id) {
                return reserva;
            }
        }
        return null;
    }

    @GetMapping("/reservas/huesped/{id_huesped}")
    public Reserva getReservasByHuespedId(@PathVariable int id_huesped) {

        for (Reserva reserva : reservas) {  
            if (reserva.getId_huesped() == id_huesped) {
                return reserva;
            }
        }
        return null;

    }

    @GetMapping("/reservas/habitacion/{id_habitacion}")
    public Reserva getReservasByHabitacionId(@PathVariable int id_habitacion) {

        for (Reserva reserva : reservas) {  
            if (reserva.getId_habitacion() == id_habitacion) {
                return reserva;
            }
        }
        return null;

    }

    @GetMapping("/reservas/fecha/{fecha}")
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

                for (Reserva r : reservasEnFecha) {
                    if (r.getId_habitacion() != reserva.getId_habitacion()){
                        for (Habitacion habitacion : new HabitacionController().getHabitaciones()) {
                            if (habitacion.getId() == r.getId_habitacion()) {
                                habitacionesDisponibles.add(habitacion);
                            }
                        }
                    }

                }
            }

        }

        return habitacionesDisponibles;
    }

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

    @GetMapping("/reservas/nueva")
    public String nuevaReserva() {

        HabitacionController habController = new HabitacionController();
        HuespedController huespedController = new HuespedController();


        int id_huesp = huespedController.getHuespedes().size() + 1;

        int id_reserva = reservas.size() + 1;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese los datos del huésped:");

        System.out.print("Ingrese el nombre del huésped: ");
        String nombre_huesped = scanner.nextLine();
        System.out.print("Ingrese el apellido del huésped: ");
        String apellido_huesped = scanner.nextLine();
        System.out.print("Ingrese el email del huésped: ");
        String email_huesped = scanner.nextLine();
        System.out.print("Ingrese el teléfono del huésped: ");
        String telefono_huesped = scanner.nextLine();
        System.out.print("Ingrese la dirección del huésped: ");
        String direccion_huesped = scanner.nextLine();
        System.out.print("Ingrese la ciudad del huésped: ");
        String ciudad_huesped = scanner.nextLine();
        System.out.print("Ingrese el país del huésped: ");
        String pais_huesped = scanner.nextLine();

        System.out.println("Seleccione una habitación disponible:");
        
        for (Habitacion habitacion : habController.getDisponibilidad()) {
            System.out.println("ID: " + habitacion.getId() + ", Número: " + habitacion.getNumero_habitacion() + ", Tipo: " + habitacion.getTipo_habitacion() + ", Capacidad: " + habitacion.getCapacidad() + ", Precio por noche: " + habitacion.getPrecio_por_noche());
        }
        System.out.print("Ingrese el ID de la habitación seleccionada: ");
        int id_hab = scanner.nextInt();

        System.out.println("Ingrese los datos de la reserva:");

        System.out.print("Ingrese el número de adultos: ");
        int numero_adultos = scanner.nextInt();
        System.out.print("Ingrese el número de niños: ");
        int numero_ninos = scanner.nextInt();
        System.out.print("Ingrese la fecha de check-in (yyyy-MM-dd): ");
        String fecha_check_in = scanner.next();
        System.out.print("Ingrese la fecha de check-out (yyyy-MM-dd): ");
        String fecha_check_out = scanner.next();
        System.out.print("Ingrese solicitudes especiales: ");
        scanner.nextLine(); // Consumir el salto de línea pendiente
        String solicitudes_especiales = scanner.nextLine();

        Boolean esta_cancelada = false;

        scanner.close();

        double precio_total = habController.getHabitacionById(id_hab).getPrecio_por_noche() * (Integer.parseInt(fecha_check_out.split("-")[2]) - Integer.parseInt(fecha_check_in.split("-")[2]));

        System.out.println("Reserva creada correctamente. Detalles: ");
        System.out.println("ID Reserva: " + id_reserva);
        System.out.println("ID Huésped: " + id_huesp);
        System.out.println("ID Habitación: " + id_hab);
        System.out.println("Número de adultos: " + numero_adultos);
        System.out.println("Número de niños: " + numero_ninos);
        System.out.println("Fecha de check-in: " + fecha_check_in);
        System.out.println("Fecha de check-out: " + fecha_check_out);
        System.out.println("Precio total: " + precio_total);
        System.out.println("Solicitudes especiales: " + solicitudes_especiales);

        Reserva nuevaReserva = new Reserva(id_reserva, id_huesp, id_hab, numero_adultos, numero_ninos, fecha_check_in, fecha_check_out, esta_cancelada, precio_total, solicitudes_especiales);

        reservas.add(nuevaReserva);

        return "Reserva creada correctamente";

    }
}