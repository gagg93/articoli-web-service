package com.si2001.webapp.controller;

import com.si2001.webapp.dto.ReservationDto;

import com.si2001.webapp.entity.User;
import com.si2001.webapp.security.JwtUtil;
import com.si2001.webapp.service.ReservationService;

import com.si2001.webapp.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ReservationDto>> getReservations() {
        return new ResponseEntity<>(reservationService.findAll(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/reservations/vehicle/{id}")
    public ResponseEntity<List<ReservationDto>> getReservationsByVehicle(@PathVariable int id) {
        return new ResponseEntity<>(reservationService.findByVehicleId(id), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/reservations/user/{id}")
    public ResponseEntity<List<ReservationDto>> getReservationsByUser(@PathVariable int id) {
        return new ResponseEntity<>(reservationService.findByUserId(id), new HttpHeaders(), HttpStatus.OK);
    }


    @PostMapping("/reservations/approve/{id}")
    public ResponseEntity<?> approveReservation(@PathVariable int id) throws Exception {
        reservationService.approveReservation(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/reservations/disapprove/{id}")
    public ResponseEntity<?> disapproveReservation(@PathVariable int id) throws Exception {
        reservationService.disapproveReservation(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    //metodi customer
    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationDto> getReservationsById(@PathVariable int id, HttpServletRequest httpServletRequest) throws Exception {
        User user = extractUserFromJwt(httpServletRequest);
        List<ReservationDto> reservationDtos = reservationService.findByUserId(userService.findByUsername(user.getUsername()).getId());
        ReservationDto res = reservationService.findById(id);
        if (reservationDtos.contains(res)){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(reservationService.findById(id), new HttpHeaders(), HttpStatus.OK);

    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<?> editReservationsById(@PathVariable int id, @RequestBody ReservationDto reservation, HttpServletRequest httpServletRequest) throws Exception {
        User user = extractUserFromJwt(httpServletRequest);
        List<ReservationDto> reservationDtos = reservationService.findByUserId(userService.findByUsername(user.getUsername()).getId());
        if (id != reservation.getId()){ //|| !reservationDtos.contains(reservation)){
            return new ResponseEntity<>("stai hackerando", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        reservationService.updateReservation(reservation);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> deleteReservationsById(@PathVariable int id, HttpServletRequest httpServletRequest) throws Exception {
        User user = extractUserFromJwt(httpServletRequest);
        List<ReservationDto> reservationDtos = reservationService.findByUserId(userService.findByUsername(user.getUsername()).getId());
        ReservationDto reservation = reservationService.findById(id);
        if (id != reservation.getId()){// || !reservationDtos.contains(reservation)){
            return new ResponseEntity<>("stai hackerando", new HttpHeaders(), HttpStatus.BAD_REQUEST);

        }
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/reservations/my/self")
    public ResponseEntity<List<ReservationDto>> getMyReservations(HttpServletRequest httpServletRequest) {
        User user=extractUserFromJwt(httpServletRequest);
        return new ResponseEntity<>(reservationService.findByUserId(user.getId()), new HttpHeaders(), HttpStatus.OK);
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
    public ResponseEntity<String> saveNewReservations(@RequestBody ReservationDto reservation, HttpServletRequest httpServletRequest){
        User user = extractUserFromJwt(httpServletRequest);
        reservation.setApproved(null);
        if (!reservation.getUser().equals(user.getUsername())){
            return new ResponseEntity<>("stai hackerando", new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
        try {
            reservationService.saveReservation(reservation);
        }catch (Exception e){
            return new ResponseEntity<>("problemi", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("ok", new HttpHeaders(), HttpStatus.OK);
    }


}
