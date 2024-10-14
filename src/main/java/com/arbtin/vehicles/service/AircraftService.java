package com.arbtin.vehicles.service;

import com.arbtin.vehicles.repository.AircraftRepository;
import com.arbtin.vehicles.entity.Aircraft;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    public Aircraft saveAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    public List<Aircraft> findAllAircraft() { return aircraftRepository.findAll(); }

    public Aircraft findAircraftById(Long id) { return aircraftRepository.findById(id).get(); }
    public Aircraft updateAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    public void deleteAircraftById(long id) {
        aircraftRepository.deleteById(id);
    }

    public void deleteMedevacRequest(Aircraft aircraft) {
        aircraftRepository.delete(aircraft);
    }

    @Transactional
    public List<Aircraft> updateAllAircraft(List<Aircraft> aircraftList) {
        for (Aircraft request : aircraftList) {
//            request.setStatus("Complete");
        }
        return aircraftRepository.saveAllAndFlush(aircraftList);
    }
}