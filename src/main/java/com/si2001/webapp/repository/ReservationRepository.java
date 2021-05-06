package com.si2001.webapp.repository;

import com.si2001.webapp.entity.Reservation;
import com.si2001.webapp.entity.User;
import com.si2001.webapp.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findReservationsByVehicleId(Vehicle vehicleId);

    List<Reservation> findAllByUserId(User vehicleId);

}
