package com.arbtin.vehicles.repository;

import com.arbtin.vehicles.entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository extends JpaRepository<Pilot, Long> {
    Pilot findPilotByFirstName(String firstName);
}
