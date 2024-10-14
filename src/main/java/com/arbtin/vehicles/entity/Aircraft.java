package com.arbtin.vehicles.entity;

import jakarta.persistence.*;

@Entity
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String airframe;
    @ManyToOne
    private Pilot pilot;

    public Aircraft() {
    }

    public Aircraft(String airframe, Pilot pilot) {
        this.airframe = airframe;
        this.pilot = pilot;
    }

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

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

/*    public CrewChief getCrew_chief_id() {
        return crew_chief_id;
    }*/
}