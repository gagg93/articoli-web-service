package com.si2001.webapp.controller;

import com.si2001.webapp.dto.ReservationDto;

import com.si2001.webapp.service.ReservationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public List<ReservationDto> getReservations() {

        return (reservationService.findAll());
    }

    @GetMapping("/reservations/vehicle/{id}")
    public List<ReservationDto> getReservationsByVehicle(@PathVariable int id) {

        return reservationService.findByVehicleId(id);
    }

    @GetMapping("/reservations/user/{id}")
    public List<ReservationDto> getReservationsByUser(@PathVariable int id) {

        return reservationService.findByUserId(id);
    }

    @GetMapping("/reservations/{id}")
    public ReservationDto getReservationsById(@PathVariable int id) throws Exception {

        return reservationService.findById(id);
    }

    @PostMapping("/approve/{id}")
    public String approveReservation(@PathVariable int id) throws Exception {
        reservationService.approveReservation(id);
        return "approved {id}";
    }

    @PostMapping("/disapprove/{id}")
    public String disapproveReservation(@PathVariable int id) throws Exception {
        reservationService.disapproveReservation(id);
        return "disapproved {id}";
    }

    @PostMapping("/reservations/new")
    public String saveNewReservations(@RequestBody ReservationDto reservation) {
        try {
            reservationService.saveReservation(reservation);
        }catch (Exception e){
            return e.getMessage();
        }
        return "ok";
    }

    @PutMapping("/reservations")
    public void editReservationsById(@RequestBody ReservationDto reservation) throws Exception {
        reservationService.updateReservation(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservationsById(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }
}
