package com.arbtin.vehicles.aircraft;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AircraftController.class)
class AircraftControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AircraftRepository aircraftRepository;

    Aircraft aircraft1;
    Aircraft aircraft2;

    @BeforeEach
    public void setUp() {
        aircraft1 = new Aircraft("monowing", "Bob");
        aircraft2 = new Aircraft("biplane", "Eddie");
    }

    @Test
    void getAllAircraftShouldReturnAllAircraft() throws Exception {
        // Arrange
        List<Aircraft> expectedAircraft = new ArrayList<>();
        expectedAircraft.add(aircraft1);
        expectedAircraft.add(aircraft2);
        when(aircraftRepository.findAll()).thenReturn(expectedAircraft);

        // Act
        mockMvc.perform(get("/api/v1/aircraft"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedAircraft)));
        // Assert
        verify(aircraftRepository, times(1)).findAll();
    }

    @Test
    void shouldPostNewAircraft() throws Exception {
        aircraft1.setId(1L);
        when(aircraftRepository.save(Mockito.any(Aircraft.class))).thenReturn(aircraft1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/aircraft")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(aircraft1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.airframe").value("monowing"))
                .andExpect(jsonPath("$.pilot").value("Bob"))
                .andReturn();

        ArgumentCaptor<Aircraft> argument = ArgumentCaptor.forClass(Aircraft.class);
        verify(aircraftRepository).save(argument.capture());
        assertEquals(aircraft1.getId(), argument.getValue().getId());
    }

    @Test
    void shouldFindOneAircraft() throws Exception {
        when(aircraftRepository.findById(aircraft1.getId())).thenReturn(Optional.of(aircraft1));
        aircraft1.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/aircraft/{id}", aircraft1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(aircraft1)));

        //mockMvc.perform(get("/api/v1/aircraft/{id}", aircraft1.getId())).andExpect(status().isOk());
    }
    @Test
    void shouldReturnAircraftById() throws Exception {
        aircraftRepository.save(new Aircraft("monoplane", "Bob"));
        Aircraft savedAircraft = aircraftRepository.save(new Aircraft("biplane", "Eddie"));
        savedAircraft.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/aircraft/{id}", savedAircraft.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(savedAircraft)));
    }

    @Test
    void shouldDeleteAircraft() throws Exception {
        when(aircraftRepository.findById(aircraft1.getId())).thenReturn(Optional.of(aircraft1));
        aircraft1.setId(1L);

        mockMvc.perform(delete("/api/v1/aircraft/{id}", aircraft1.getId()))
                .andExpect(status().isNoContent());
    }

//    @Test
//    void shouldWorkRelationship () throws Exception {
//        CrewChief crewChief = new CrewChief("Torres");
//        crewChiefRepository.save(crewChief);
//        Aircraft aircraft = new Aircraft("Skyhawk", "Mavrick", crewChief);
//        Aircraft aircraft1 = new Aircraft("Raptor", "Goose", crewChief);
//        aircraftRepository.save(aircraft);
//        aircraftRepository.save(aircraft1);
//
//        List<Aircraft> newAircraft = aircraftRepository.findAll();
//        List<CrewChief> crewChiefs = crewChiefRepository.findAll();
//
//        crewChiefs.get(0).showInfo();
//    }



}
