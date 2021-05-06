package com.si2001.webapp.service;

import com.si2001.webapp.dto.ReservationDto;

import java.util.List;


public interface ReservationService {
    List<ReservationDto> findAll();

    ReservationDto findById(int id) throws Exception;

    void saveReservation(ReservationDto reservation) throws Exception;

    void updateReservation(ReservationDto reservation) throws Exception;

    void deleteReservation(int id);

    List<ReservationDto> findByVehicleId(int id);

    List<ReservationDto> findByUserId(int id);

    void approveReservation(int id) throws Exception;

    void disapproveReservation(int id) throws Exception;
}
