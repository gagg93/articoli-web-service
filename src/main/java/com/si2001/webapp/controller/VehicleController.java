package com.si2001.webapp.controller;



import com.si2001.webapp.entity.Vehicle;

import com.si2001.webapp.service.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {


    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles")
    public List<Vehicle> getVehicles() {

        return vehicleService.findAll();
    }

    @GetMapping("/vehicles/targa/{targa}")
    public Vehicle getVehicleById(@PathVariable String targa) {
        return vehicleService.findByTarga(targa);
    }

    @GetMapping("/vehicles/{id}")
    public Vehicle getVehicleById(@PathVariable int id) {
        return vehicleService.findById(id);
    }

    @PostMapping("/vehicles")
    public String getVehicleById(@RequestBody Vehicle vehicle) throws Exception {
        try{
            vehicleService.updateVehicle(vehicle);
        }catch (Exception e){
            return e.getMessage();
        }
        return "ok";
    }

    @PutMapping("/vehicles")
    public String editVehicleById(@RequestBody Vehicle vehicle) throws Exception {
        try{
            vehicleService.updateVehicle(vehicle);
        }catch (Exception e){
            return e.getMessage();
        }
        return "ok";
    }


    @DeleteMapping("/vehicles/{id}")
    public void deleteVehicleById(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
    }
}
