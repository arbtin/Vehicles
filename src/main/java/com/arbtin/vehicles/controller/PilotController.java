package com.arbtin.vehicles.controller;

import com.arbtin.vehicles.entity.Pilot;
import com.arbtin.vehicles.service.PilotService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pilot")
public class PilotController {

    private final PilotService pilotService;

    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @PostMapping()
    public ResponseEntity<Pilot> createPilot(@RequestBody Pilot pilot) {
        Pilot newPilot = pilotService.savePilot(pilot);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/pilot" + newPilot.getId()));

        return new ResponseEntity<>(newPilot, headers, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Pilot>> getPilot() {
        List<Pilot> pilots = pilotService.findAllPilots();
        return ResponseEntity.ok(pilots);
    }

    @GetMapping("{id}")
    public Pilot getPilotById(@PathVariable Long id) {
        return pilotService.findPilotById(id);
    }

}
