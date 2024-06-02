package com.arbtin.vehicles.aircraft;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AircraftController {

    private final AircraftRepository aircraftRepository;

    public AircraftController(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @PostMapping("/aircraft")
    @ResponseStatus(HttpStatus.CREATED)
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    @GetMapping("/aircraft")
    @ResponseStatus(HttpStatus.OK)
    public List<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    @GetMapping("/aircraft/{id}")
    @ResponseStatus(HttpStatus.OK)
//    @ModelAttribute("Aircraft")
    public Aircraft getAircraftById(@ModelAttribute("Aircraft") @PathVariable Long id) {
        Aircraft aircraft = aircraftRepository.findById(id).orElse(null);
        if (aircraft == null) {
            throw new AircraftNotFoundException("Aircraft not found with id: " + id);
        }
        aircraftRepository.getReferenceById(id); // findbyId
        return aircraft;
        //return aircraftRepository.findById(id).orElseThrow();
    }

    @DeleteMapping("/aircraft/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Aircraft deleteListItem(@PathVariable Long id) {
        Aircraft targetAircraft = aircraftRepository.findById(id).orElse(null);
        aircraftRepository.deleteById(id);
        return targetAircraft;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class AircraftNotFoundException extends RuntimeException {
        public AircraftNotFoundException(String message) {
            super(message);
        }
    }
}