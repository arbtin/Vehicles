package com.arbtin.vehicles.controller;

import com.arbtin.vehicles.entity.Aircraft;
import com.arbtin.vehicles.entity.Pilot;
import com.arbtin.vehicles.repository.AircraftRepository;
import com.arbtin.vehicles.repository.PilotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AircraftControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private PilotRepository pilotRepository;

    Pilot snoopy;
    Aircraft newAircraft;

    @BeforeEach
    void setup() {
        snoopy = new Pilot(1L, "Snoopy", "the Beagle", 10);
        newAircraft = new Aircraft("doghouse", snoopy);
    }

    @Test
    public void shouldCreateAircraft() throws Exception {
        String newPilot = objectMapper.writeValueAsString(snoopy);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/pilot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPilot));

        String doghouse = objectMapper.writeValueAsString(newAircraft);

        mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/aircraft")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(doghouse))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.airframe").value("doghouse"))
                .andExpect(jsonPath("$.pilot.id").value(1))
                .andExpect(jsonPath("$.pilot.firstName").value("Snoopy"))
                .andExpect(jsonPath("$.pilot.lastName").value("the Beagle"))
                .andExpect(jsonPath("$.pilot.age").value(10));
    }

    @Test
    void shouldGetAllAircraft() throws Exception {
        pilotRepository.save(snoopy);
        aircraftRepository.save(newAircraft);

        mockMvc.perform(get("/api/aircraft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].airframe").value("doghouse"))
                .andExpect(jsonPath("$[0].pilot.id").value(1L));
    }

    @Test
    void shouldGetAircraftById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aircraft/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.airframe").value("doghouse"))
                .andExpect(jsonPath("$.pilot.id").value(1L))
                .andExpect(jsonPath("$.pilot.firstName").value("Snoopy"))
                .andExpect(jsonPath("$.pilot.lastName").value("the Beagle"))
                .andExpect(jsonPath("$.pilot.age").value(10));
    }
}
