package com.arbtin.vehicles.controller;

import com.arbtin.vehicles.entity.Aircraft;
import com.arbtin.vehicles.dto.AircraftDTO;
import com.arbtin.vehicles.entity.Pilot;
import com.arbtin.vehicles.service.AircraftService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AircraftController.class)
class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AircraftService aircraftService;

    Pilot snoopy = new Pilot(1L, "Snoopy", "the Beagle", 10);
    Pilot baron = new Pilot(2L, "The Red", "Baron", 30);
    Aircraft aircraft = new Aircraft("doghouse", snoopy);
    List<Aircraft> aircrafts = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        aircrafts.add(aircraft);
        aircraft.setId(1L);

        Mockito.when(aircraftService.saveAircraft(Mockito.any(Aircraft.class))).thenReturn(aircraft);
        Mockito.when(aircraftService.findAircraftById(Mockito.anyLong())).thenReturn(aircraft);
        Mockito.when(aircraftService.findAllAircraft()).thenReturn(aircrafts);
    }

    @Test
    void shouldCreateAircraft() throws Exception {
        String doghouseJson = objectMapper.writeValueAsString(aircraft);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/aircraft")
                .contentType(MediaType.APPLICATION_JSON)
                .content(doghouseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.airframe").value(1))
                .andExpect(jsonPath("$.pilot.id").value(1))
                .andExpect(jsonPath("$.pilot.firstName").value(1))
                .andExpect(jsonPath("$.pilot.lastName").value(1))
                .andExpect(jsonPath("$.pilot.age").value(1));

        Mockito.verify(aircraftService).saveAircraft(any(Aircraft.class));
    }

    @Test
    void shouldGetAllAircraft() throws Exception {
        mockMvc.perform(get("/api/aircraft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].airframe").value(1))
                .andExpect(jsonPath("$[0].pilot.id").value(1))
                .andExpect(jsonPath("$[0].pilot.firstName").value(1));

        Mockito.verify(aircraftService).findAllAircraft();
    }

    @Test
    void shouldGetAircraftById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/aircraft/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.airframe").value(1))
                .andExpect(jsonPath("$.pilot.firstName").value(1));

        Mockito.verify(aircraftService).findAircraftById(1L);
    }

    @Test
    void oldShouldGetAllAircraft() throws  Exception {
        var aircraft1DTO = new AircraftDTO(1L, "monowing", snoopy);
        var aircraft2DTO = new AircraftDTO(2L, "biplane", baron);

        List<AircraftDTO> allAircraft = List.of(aircraft1DTO, aircraft2DTO);

        when(aircraftService.findAllAircraft()).thenReturn(allAircraft);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/aircraft"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(allAircraft)));

    }
    @Test
    void getAllAircraftShouldReturnAllAircraft() throws Exception {
        var aircraft1DTO = new AircraftDTO(1L, "monowing", snoopy);
        var aircraft2DTO = new AircraftDTO(2L, "biplane", baron);

        // Arrange
        List<AircraftDTO> expectedAircraft = new ArrayList<>();
        expectedAircraft.add(aircraft1DTO);
        expectedAircraft.add(aircraft2DTO);
        when(aircraftService.findAllAircraft()).thenReturn(expectedAircraft);

        // Act
        mockMvc.perform(get("/api/v1/aircraft"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedAircraft)));
        // Assert
        verify(aircraftService, times(1)).findAllAircraft();
    }

    @Test
    void shouldPostNewAircraft() throws Exception {
        var aircraft1 = new Aircraft("monowing", snoopy);
        aircraft1.setId(1L);
//        when(aircraftService.saveAircraft(Mockito.any(Aircraft.class))).thenReturn(aircraft1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/aircraft")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(aircraft1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.airframe").value("monowing"))
                .andExpect(jsonPath("$.pilot").value("Bob"))
                .andReturn();

        ArgumentCaptor<Aircraft> argument = ArgumentCaptor.forClass(Aircraft.class);
        verify(aircraftService).saveAircraft(argument.capture());
        assertEquals(aircraft1.getId(), argument.getValue().getId());
    }

    @Test
    void shouldReturnOneAircraftById() throws Exception {
        var aircraft1 = new Aircraft("monowing", snoopy);
        // Mock the behavior of aircraftRepository.findById()
        when(aircraftService.findAircraftById(1L)).thenReturn(Optional.of(aircraft1));
        aircraft1.setId(1L);

        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/aircraft/{id}", aircraft1.getId().intValue()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(aircraft1)))
                .andReturn();

        // Example assertions on the content of the response
        String content = result.getResponse().getContentAsString();
        assertThat(content).contains(new ObjectMapper().writeValueAsString(aircraft1));

// Example assertions on response headers
        assertThat(result.getResponse().getHeader("Content-Type")).isEqualTo("application/json");

// Example assertions on specific attributes of the response
        assertThat(result.getResponse().getContentLength()).isEqualTo(0);

// Example assertions on model attributes passed to the view
        ModelAndView mav = result.getModelAndView();
        //System.out.println("Model keys: " + mav.getModel().keySet());
        //assertThat(mav.getModel()).containsKey("Aircraft");

// Example assertions on forwarded or redirected URLs
        // assertThat(result.getResponse().getRedirectedUrl()).isEqualTo("/expected-redirect-url");

// Example assertions on session attributes
        HttpSession session = result.getRequest().getSession();
        //assertThat(session.getAttribute("yourSessionAttribute")).isEqualTo("expectedValue");

// Example assertions on status code
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    }


    @Test
    void example_shouldReturnOneAircraftById() throws Exception {
        var aircraft1 = new Aircraft("monowing", snoopy);
        // Mock the behavior of aircraftRepository.findById()
        when(aircraftService.findAircraftById(1L)).thenReturn(Optional.of(aircraft1));
        aircraft1.setId(1L);

       MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/aircraft/{id}", aircraft1.getId().intValue()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(aircraft1)))
                        .andReturn();

        // Example assertions on the content of the response
        byte[] bytes = result.getResponse().getContentAsByteArray();
        //byte[] bytes = content;

        String json = new String(bytes, StandardCharsets.UTF_8); // Adjust the character encoding if necessary

// Deserialize the JSON string into a Java object using an ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        Aircraft aircraft = objectMapper.readValue(json, Aircraft.class);

        String expectedJson = new ObjectMapper().writeValueAsString(aircraft1).trim();
        //String actualJson = content.trim();
        System.out.println("Actual: " + expectedJson);
        //assertThat(expectedJson).contains(aircraft);
        assertThat(expectedJson).contains(new ObjectMapper().writeValueAsString(aircraft1));

// Example assertions on response headers
        //assertThat(result.getResponse().getHeader("Content-Type")).isEqualTo("application/json");

// Example assertions on specific attributes of the response
        //assertThat(result.getResponse().getContentLength()).isEqualTo(10);

// Example assertions on model attributes passed to the view
        ModelAndView mav = result.getModelAndView();
        //System.out.println("Model keys: " + mav.getModel().keySet());
        //assertThat(mav.getModel()).containsKey("Aircraft");
    }

    @Test
    void shouldDeleteAircraft() throws Exception {
        var aircraft1 = new Aircraft("monowing", snoopy);
        aircraft1.setId(1L);
        when(aircraftService.findAircraftById(aircraft1.getId())).thenReturn(Optional.of(aircraft1));

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
