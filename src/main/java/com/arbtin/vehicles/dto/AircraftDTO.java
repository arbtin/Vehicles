package com.arbtin.vehicles.dto;

import com.arbtin.vehicles.entity.Pilot;

public record AircraftDTO (
    Long id,
    String airframe,
    Pilot pilot
    ) {
    public AircraftDTO(String airframe, Pilot pilot) {
        this(null, airframe, pilot);
    }
}

