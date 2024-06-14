package com.arbtin.vehicles.aircraft;

import com.arbtin.vehicles.aircraft.Aircraft;
import com.arbtin.vehicles.aircraft.AircraftRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AircraftService {
    private final AircraftRepository airRepo;

    public AircraftService(AircraftRepository airRepo) {
        this.airRepo = airRepo;
    }

    public Optional<Aircraft> findAircraftById(long id) {
        return airRepo.findById(id);
    }

    @Transactional
    public List<Aircraft> findAllAircraft() {
        var all = airRepo.findAll();
        return airRepo.findAll();
    }

    public Aircraft saveAircraft(Aircraft aircraft) {
        return airRepo.saveAndFlush(aircraft);
    }

    public void deleteAircraftById(long id) {
        airRepo.deleteById(id);
    }

    public void deleteMedevacRequest(Aircraft aircraft) {
        airRepo.delete(aircraft);
    }

    @Transactional
    public List<Aircraft> updateAllAircraft(List<Aircraft> aircraftList) {
        for (Aircraft request : aircraftList) {
//            request.setStatus("Complete");
        }
        return airRepo.saveAllAndFlush(aircraftList);
    }
}