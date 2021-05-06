package com.si2001.webapp.service;



import com.si2001.webapp.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> findAll();

    Vehicle findById(int id);

    void updateVehicle(Vehicle vehicle) throws Exception;

    void deleteVehicle(int id);

    Vehicle findByTarga(String targa);
}
