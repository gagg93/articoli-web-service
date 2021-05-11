package com.si2001.webapp.controller;



import com.si2001.webapp.entity.Vehicle;

import com.si2001.webapp.service.VehicleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class VehicleController {


    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    //metodi per tutti
    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getVehicles() {

        return new ResponseEntity<>(vehicleService.findAll(), new HttpHeaders(), HttpStatus.OK);
    }

    //metodi admin
    @GetMapping("/vehicles/targa/{targa}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable String targa) {
        return new ResponseEntity<>(vehicleService.findByTarga(targa), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable int id) {
        return new ResponseEntity<>(vehicleService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/vehicles/new")
    public ResponseEntity<?> newVehicle(@RequestBody Vehicle vehicle) {
        try{
            vehicleService.updateVehicle(vehicle);
        }catch (Exception e){
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/vehicles/{id}")
    public ResponseEntity<?> editVehicleById(@RequestBody Vehicle vehicle) {
        try{
            vehicleService.updateVehicle(vehicle);
        }catch (Exception e){
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<?> deleteVehicleById(@PathVariable int id) {
        try{
            vehicleService.deleteVehicle(id);
        }catch (Exception e){
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
