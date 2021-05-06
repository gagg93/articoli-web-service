package com.si2001.webapp.service;

import com.si2001.webapp.dto.ReservationDto;
import com.si2001.webapp.entity.Reservation;

import com.si2001.webapp.entity.User;
import com.si2001.webapp.entity.Vehicle;
import com.si2001.webapp.repository.ReservationRepository;

import com.si2001.webapp.repository.UserRepository;
import com.si2001.webapp.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository repository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public ReservationServiceImpl(ReservationRepository repository, VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.repository = repository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ReservationDto> findAll() {
        List<Reservation> reservations = repository.findAll();
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation var:
             reservations) {
            reservationDtos.add(new ReservationDto(var));
        }
        return reservationDtos;
    }

    @Override
    public ReservationDto findById(int id) throws Exception {
        Reservation reservation= repository.findById(id).orElseThrow(() -> new Exception("reservation not found - " + id));
        ReservationDto reservationDto;
        reservationDto = new ReservationDto(reservation);
        return reservationDto;
    }

    @Override
    public void saveReservation(ReservationDto reservationDto) throws Exception {
        Reservation reservation = copyReservation(reservationDto);
        if (canBook(reservationDto)) {
            repository.save(reservation);
        }
    }

    @Override
    public void updateReservation(ReservationDto reservationDto) throws Exception {
        Reservation reservation = copyReservation(reservationDto);
        reservation.setId(reservationDto.getId());
        if(canBook(reservationDto)){
            repository.save(reservation);
        }
    }

    @Override
    public void deleteReservation(int id){
        repository.deleteById(id);
    }

    @Override
    public List<ReservationDto> findByVehicleId(int id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        List<Reservation> reservations = repository.findReservationsByVehicleId(vehicle.orElse(null));
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation var:
                reservations) {
            reservationDtos.add(new ReservationDto(var));
        }
        return reservationDtos;
    }

    @Override
    public List<ReservationDto> findByUserId(int id) {
        Optional<User> user = userRepository.findById(id);
        List<Reservation> reservations = repository.findAllByUserId(user.orElse(null));
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation var:
                reservations) {
            reservationDtos.add(new ReservationDto(var));
        }
        return reservationDtos;
    }

    @Override
    public void approveReservation(int id) throws Exception {
        ReservationDto reservation= findById(id);
        reservation.setApproved(true);
        saveReservation(reservation);
    }

    @Override
    public void disapproveReservation(int id) throws Exception {
        ReservationDto reservation= findById(id);
        reservation.setApproved(false);
        saveReservation(reservation);
    }

    private boolean canBook(ReservationDto reservationDto) throws Exception {
        Date twoDays=new Date(System.currentTimeMillis() + 172800000);
        if(reservationDto.getResBegin().before(twoDays)){
            throw new Exception("troppo tardi");
        }
        if(reservationDto.getResBegin().after(reservationDto.getResEnd())  ) {
            throw new Exception("date invertite");
        }
        Optional<Vehicle> vehicle = vehicleRepository.findVehicleByTarga(reservationDto.getVehicle());
        List<Reservation> reservationList = new ArrayList<>();
        if (vehicle.isPresent()){
            reservationList = repository.findReservationsByVehicleId(vehicle.get());
        }
        for (Reservation reservation:
                reservationList) {
            if ((reservationDto.getId() != reservation.getId()) &&
                    ((reservationDto.getResEnd().equals(reservation.getResEnd()) || reservationDto.getResBegin().equals(reservation.getResEnd())) ||
                            (reservationDto.getResBegin().before(reservation.getResBegin()) && reservationDto.getResEnd().after(reservation.getResEnd())) ||
                            (reservationDto.getResBegin().after(reservation.getResBegin()) && reservationDto.getResBegin().before(reservation.getResEnd())) ||
                            (reservationDto.getResEnd().after(reservation.getResBegin()) && reservationDto.getResEnd().before(reservation.getResEnd())))) {
                throw new Exception("macchina prenotata");

            }
        }
        return true;
    }

    private Reservation copyReservation(ReservationDto reservationDto) throws Exception {
        Reservation reservation = new Reservation();
        reservation.setResBegin(reservationDto.getResBegin());
        reservation.setResEnd(reservationDto.getResEnd());
        reservation.setUserId(userRepository.findUserByUsername(reservationDto.getUser()).orElseThrow(() -> new Exception("User not found " + reservationDto.getUser())));
        reservation.setVehicleId(vehicleRepository.findVehicleByTarga(reservationDto.getVehicle()).orElseThrow(() -> new Exception("Vehicle not found " + reservationDto.getVehicle())));
        return reservation;
    }

}
