package com.arbtin.vehicles.controller;

import com.arbtin.vehicles.dto.AircraftDTO;
import com.arbtin.vehicles.service.AircraftService;
import com.arbtin.vehicles.entity.Aircraft;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @PostMapping()
    public ResponseEntity<Aircraft> createAircraft(@RequestBody Aircraft aircraft) {
        return ResponseEntity.ok( aircraftService.saveAircraft(aircraft));
    }

    @GetMapping()
    public List<Aircraft> getAircraft() {
        return aircraftService.findAllAircraft();
    }

    @GetMapping("/{id}")
    public Aircraft getAircraftById(@PathVariable Long id) {
        return aircraftService.findAircraftById(id);
    }

    @DeleteMapping("/aircraft/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Aircraft deleteListItem(@PathVariable Long id) {
        Aircraft targetAircraft = aircraftService.findAircraftById(id); //.orElse(null);
        aircraftService.deleteAircraftById(id);
        return targetAircraft;
    }

    @PatchMapping("/aircraft")
    public ResponseEntity<List<Aircraft>> updateAircraft(@RequestBody List<Aircraft> listOfAircraft) {
        return new ResponseEntity<>(aircraftService.updateAllAircraft(listOfAircraft), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class AircraftNotFoundException extends RuntimeException {
        public AircraftNotFoundException(String message) {
            super(message);
        }
    }
}