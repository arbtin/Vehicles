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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PilotControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PilotRepository pilotRepository;

    Pilot snoopy;

    @BeforeEach
    void setup() {
        snoopy = new Pilot(1L, "Snoopy", "the Beagle", 10);
        Pilot baron = new Pilot(1L, "Red", "Baron", 30);
        List<Pilot> pilots = new ArrayList<>(List.of(snoopy, baron));
    }

    @Test
    public void shouldCreatePilot() throws Exception {
        String pilotJson = objectMapper.writeValueAsString(snoopy);

        mockMvc.perform(post("/api/pilot")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(pilotJson))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.firstName").value("Snoopy"))
                .andExpect(jsonPath("$.lastName").value("the Beagle"))
                .andExpect(jsonPath("$.age").value(10));
    }

    @Test
    void shouldGetAllPilots() throws Exception {
        pilotRepository.save(snoopy);

        mockMvc.perform(get("/api/pilot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Snoopy"))
                .andExpect(jsonPath("$[0].lastName").value("the Beagle"))
                .andExpect(jsonPath("$[0].age").value(1L));
    }

    @Test
    void shouldGetPilotById() throws Exception {
        mockMvc.perform(get("/api/aircraft/" + snoopy.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Snoopy"))
                .andExpect(jsonPath("$.lastName").value("the Beagle"))
                .andExpect(jsonPath("$.age").value(10));
    }
}
