package com.arbtin.vehicles.service;

import com.arbtin.vehicles.entity.Pilot;
import com.arbtin.vehicles.repository.PilotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PilotService {

    private PilotRepository pilotRepository;
    public PilotService(PilotRepository pilotRepository) {
        this.pilotRepository = pilotRepository;
    }

    public Pilot savePilot(Pilot pilot) {
        return pilotRepository.save(pilot);
    }

    public List<Pilot> findAllPilots() {
        return pilotRepository.findAll();
    }

    public Pilot findPilotByName(String name) {
        return pilotRepository.findPilotByFirstName(name);
    }

    public Pilot findPilotById(Long id) {
        return pilotRepository.findById(id).orElse(null);
    }

    public Pilot getPilotById(Long id) {
        for (Pilot pilot : pilotRepository.findAll()) {
            if(pilot.getId().equals(id)) {
                return pilot;
            }
        }
        return null;
    }
}
