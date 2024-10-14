package com.arbtin.vehicles.controller;

import com.arbtin.vehicles.dto.AircraftDTO;
import com.arbtin.vehicles.entity.Pilot;
import com.arbtin.vehicles.service.PilotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PilotController.class)
public class PilotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PilotService pilotService;

    Pilot snoopy = new Pilot(1L, "Snoopy", "the Beagle", 10);
    Pilot baron = new Pilot(2L, "Red", "Baron", 30);

    @BeforeEach
    public void setUp() {
        List<Pilot> pilots = new ArrayList<>();
        pilots.add(snoopy);
        pilots.add(baron);

        Mockito.when(pilotService.savePilot(Mockito.any(Pilot.class))).thenReturn(snoopy);
        Mockito.when(pilotService.findPilotById(Mockito.anyLong())).thenReturn(snoopy);
        Mockito.when(pilotService.findAllPilots()).thenReturn(pilots);
    }

    @Test
    void shouldCreateNewPilot() throws Exception {
        String snoopyJson = objectMapper.writeValueAsString(snoopy);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pilot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(snoopyJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Snoopy"))
                .andExpect(jsonPath("$.lastName").value("the Beagle"))
                .andExpect(jsonPath("$.age").value(10));

        Mockito.verify(pilotService).savePilot(refEq(snoopy));
    }

    @Test
    void shouldGetAllPilots() throws Exception {
        mockMvc.perform(get("/api/pilot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("Snoopy"))
                .andExpect(jsonPath("$[0].lastName").value("the Beagle"))
                .andExpect(jsonPath("$[0].age").value(10));

        Mockito.verify(pilotService).findAllPilots();
    }

    @Test
    void shouldGetPilotById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pilot/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Snoopy"))
                .andExpect(jsonPath("$.lastName").value("the Beagle"))
                .andExpect(jsonPath("$.age").value(10));

        Mockito.verify(pilotService).findPilotById(1L);
    }
}
