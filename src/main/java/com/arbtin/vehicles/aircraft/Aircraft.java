package com.arbtin.vehicles.aircraft;

import jakarta.persistence.*;
//import org.example.fullstackguide.pilot.CrewChief;

@Entity
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String airframe;
    private String pilot;

/*
    @ManyToOne()
    @JoinColumn(name = "crew_chief_id")
    private CrewChief crew_chief_id;

    public Aircraft() {
    }
*/

    public Aircraft(String airframe, String pilot) {
        this.airframe = airframe;
        this.pilot = pilot;
    }

    public Aircraft() {

    }

/*
    public Aircraft(String airframe, String pilot, CrewChief crewChief) {
        this.airframe = airframe;
        this.pilot = pilot;
        this.crew_chief_id = crewChief;
    }
*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirframe() {
        return airframe;
    }

    public void setAirframe(String airframe) {
        this.airframe = airframe;
    }

    public String getPilot() {
        return pilot;
    }

    public void setPilot(String pilot) {
        this.pilot = pilot;
    }

/*    public CrewChief getCrew_chief_id() {
        return crew_chief_id;
    }

    public void setCrew_chief_id(CrewChief crew_chief_id) {
        this.crew_chief_id = crew_chief_id;
    }*/
}