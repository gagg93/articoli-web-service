package com.si2001.webapp.service;


import com.si2001.webapp.entity.Vehicle;
import com.si2001.webapp.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository repository;

    public VehicleServiceImpl(VehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Vehicle> findAll() {

        return  repository.findAll();
    }

    @Override
    public Vehicle findById(int id) {
        Optional<Vehicle> vehicle = repository.findById(id);
        return vehicle.orElse(null);
    }

    @Override
    public void updateVehicle(Vehicle vehicle) throws Exception {
        Optional<Vehicle> temp = repository.findVehicleByTarga(vehicle.getTarga());
        if (!temp.isPresent() || temp.get().getId() == vehicle.getId()) {
            repository.save(vehicle);
        }else{
            throw new Exception("targa precedentemente immatricolata");
        }
    }

    @Override
    public void deleteVehicle(int id) {
        repository.deleteById(id);
    }

    @Override
    public Vehicle findByTarga(String targa) {
        Optional<Vehicle> vehicle = repository.findVehicleByTarga(targa);
        return vehicle.orElse(null);
    }
}
