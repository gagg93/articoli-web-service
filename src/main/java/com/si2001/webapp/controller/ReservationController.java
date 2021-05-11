package com.si2001.webapp.controller;

import com.si2001.webapp.dto.ReservationDto;

import com.si2001.webapp.entity.User;
import com.si2001.webapp.security.JwtUtil;
import com.si2001.webapp.service.ReservationService;

import com.si2001.webapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class ReservationController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final ReservationService reservationService;

    public ReservationController(UserService userService, JwtUtil jwtUtil, ReservationService reservationService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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


    @PostMapping("/reservations/approve/{id}")
    public void approveReservation(@PathVariable int id) throws Exception {
        reservationService.approveReservation(id);
    }

    @PostMapping("/reservations/disapprove/{id}")
    public void disapproveReservation(@PathVariable int id) throws Exception {
        reservationService.disapproveReservation(id);
    }

    //metodi customer
    @GetMapping("/reservations/{id}")
    public ReservationDto getReservationsById(@PathVariable int id, HttpServletRequest httpServletRequest) throws Exception {
        User user = extractUserFromJwt(httpServletRequest);
        List<ReservationDto> reservationDtos = reservationService.findByUserId(userService.findByUsername(user.getUsername()).getId());
        ReservationDto res = reservationService.findById(id);
        if (reservationDtos.contains(res)){
            throw new Exception("stai provando ad hackerare");
        }
        return reservationService.findById(id);
    }

    @PutMapping("/reservations/{id}")
    public void editReservationsById(@PathVariable int id, @RequestBody ReservationDto reservation, HttpServletRequest httpServletRequest) throws Exception {
        User user = extractUserFromJwt(httpServletRequest);
        List<ReservationDto> reservationDtos = reservationService.findByUserId(userService.findByUsername(user.getUsername()).getId());
        if (id != reservation.getId()){ //|| !reservationDtos.contains(reservation)){
            throw new Exception("stai provando ad hackerare");
        }
        reservationService.updateReservation(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservationsById(@PathVariable int id, HttpServletRequest httpServletRequest) throws Exception {
        User user = extractUserFromJwt(httpServletRequest);
        List<ReservationDto> reservationDtos = reservationService.findByUserId(userService.findByUsername(user.getUsername()).getId());
        ReservationDto reservation = reservationService.findById(id);
        if (id != reservation.getId()){// || !reservationDtos.contains(reservation)){
            throw new Exception("stai provando ad hackerare");
        }
        reservationService.deleteReservation(id);
    }

    @GetMapping("/reservations/my/self")
    public List<ReservationDto> getMyReservations(HttpServletRequest httpServletRequest) {
        User user=extractUserFromJwt(httpServletRequest);

        return reservationService.findByUserId(user.getId());
    }

    private User extractUserFromJwt(HttpServletRequest httpServletRequest) {
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String username= null;
        String jwt;
        if (authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")){
            jwt= authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        return userService.findByUsername(username);
    }

    @PostMapping("/reservations/new")
    public String saveNewReservations(@RequestBody ReservationDto reservation, HttpServletRequest httpServletRequest) throws Exception {
        User user = extractUserFromJwt(httpServletRequest);
        reservation.setApproved(null);
        if (!reservation.getUser().equals(user.getUsername())){
                throw new Exception("stai provando ad hackerare");
            }
        try {
            reservationService.saveReservation(reservation);
        }catch (Exception e){
            return e.getMessage();
        }
        return "ok";
    }


}
