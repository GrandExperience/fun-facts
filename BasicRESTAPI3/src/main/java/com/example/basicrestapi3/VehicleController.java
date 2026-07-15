package com.example.basicrestapi3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class VehicleController {

    @Autowired
    private VehicleDao vehicleDao;

    @RequestMapping(value="/addVehicle", method= RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException{
        vehicleDao.create(newVehicle);
        return newVehicle;
    }

    @RequestMapping(value="/getVehicle/{id}", method= RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        return vehicleDao.getById(id);
    }

    @RequestMapping(value="/updateVehicle", method= RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        vehicleDao.update(newVehicle);
        return newVehicle;
    }

    @RequestMapping(value="/deleteVehicle/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) throws IOException {
        Vehicle vehicle = vehicleDao.getById(id);
        vehicleDao.delete(vehicle);
        return ResponseEntity.ok("Vehicle deleted.");
    }

    @RequestMapping(value="/getLatestVehicles", method= RequestMethod.GET)
    public List<Vehicle> getLatestVehicles() throws IOException {
        return vehicleDao.getLatest();
    }

}
