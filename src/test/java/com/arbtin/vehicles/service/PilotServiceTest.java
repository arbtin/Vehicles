package com.arbtin.vehicles.service;

import com.arbtin.vehicles.entity.Aircraft;
import com.arbtin.vehicles.entity.Pilot;
import com.arbtin.vehicles.repository.PilotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
public class PilotServiceTest {
    @Mock
    private PilotRepository pilotRepository;
    @InjectMocks
    private PilotService pilotService;
    Pilot snoopy;
    List<Pilot> pilots;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        snoopy = new Pilot(1L, "Snoopy", "the Beagle", 10);
        Pilot baron = new Pilot(2L, "The Red", "Baron", 30);

        pilots = new ArrayList<>(List.of(snoopy, baron));
    }

    @Test
    void shouldCreatePilot() {
        when(pilotRepository.save(snoopy)).thenReturn(snoopy);
        Pilot actualRequest = pilotService.savePilot(snoopy);
        verify(pilotRepository, times(1)).save(any(Pilot.class));
        assertThat(actualRequest).isEqualTo(snoopy);
    }

    @Test
    void shouldFindAllPilots() {
        when(pilotRepository.findAll()).thenReturn(pilots);
        List<Pilot> listOfPilotRequest = pilotService.findAllPilots();
        verify(pilotRepository, times(1)).findAll();
        assertThat(listOfPilotRequest).isEqualTo(pilots);
    }

    @Test
    void shouldFindPilotByName() {
        when(pilotRepository.findPilotByFirstName("Snoopy")).thenReturn(snoopy);
        Pilot actualRequest = pilotService.findPilotByName("Snoopy");
        verify(pilotRepository, times(1)).findPilotByFirstName("Snoopy");
        assertThat(actualRequest).isEqualTo(snoopy);
    }

    @Test
    void shouldFindPilotById() {
        when(pilotRepository.findById(1L)).thenReturn(Optional.of(snoopy));
        Pilot actualRequest = pilotService.findPilotById(1L);
        verify(pilotRepository, times(1)).findById(1L);
        assertThat(actualRequest).isEqualTo(snoopy);

        assertNotNull(actualRequest);
        assertEquals("Snoopy", actualRequest.getFirstName());
        assertEquals("the Beagle", actualRequest.getLastName());
        assertEquals(10, actualRequest.getAge());
    }

    @Test
    public void shouldCreateAndRetrievePilot() {
        Pilot woodstock = new Pilot(2L, "Woodstock", "the Bird", 5);
        pilotRepository.save(woodstock);
        when(pilotRepository.findById(2L)).thenReturn(Optional.of(woodstock));

        Pilot isWoodstock = pilotService.findPilotById(2L);

        assertNotNull(isWoodstock);
        assertEquals("Woodstock", isWoodstock.getFirstName());
        assertEquals("the Bird", isWoodstock.getLastName());
        assertEquals(5, isWoodstock.getAge());

        verify(pilotRepository, times(1)).save(woodstock);
    }

}
