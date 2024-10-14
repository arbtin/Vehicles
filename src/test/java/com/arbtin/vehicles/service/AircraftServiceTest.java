package com.arbtin.vehicles.service;

import com.arbtin.vehicles.dto.AircraftDTO;
import com.arbtin.vehicles.entity.Aircraft;
import com.arbtin.vehicles.entity.Pilot;
import com.arbtin.vehicles.repository.AircraftRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class AircraftServiceTest {

    @Mock
    private AircraftRepository aircraftRepository;

    @InjectMocks
    private AircraftService aircraftService;

    private Pilot snoopy = new Pilot(1L, "Snoopy", "the Beagle", 10);
    private Pilot baron = new Pilot(2L, "Red", "Baron", 30);

    private Aircraft doghouse;
    private Aircraft triplane;
    List<Aircraft> aircrafts;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doghouse = new Aircraft("doghouse", snoopy);
        triplane = new Aircraft("DR-1", baron);
        aircrafts = new ArrayList<>(List.of(doghouse, triplane));
    }

    @Test
    void shouldSaveNewAircraft() {
        when(aircraftRepository.findAll()).thenReturn(aircrafts);
        Aircraft actualRequest = aircraftService.saveAircraft(doghouse);
        verify(aircraftRepository, times(1)).findAll();
        assertThat(actualRequest).isEqualTo(doghouse);
    }

    @Test
    void shouldFindAllAircraft() {
        when(aircraftRepository.findAll()).thenReturn(aircrafts);
        List<Aircraft> listOfAircraftRequest = aircraftService.findAllAircraft();
        verify(aircraftRepository, times(1)).findAll();
        assertThat(listOfAircraftRequest).isEqualTo(aircrafts);
    }

    @Test
    void shouldFindAircraftById() {
        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(doghouse));
        Aircraft actualRequest = aircraftService.findAircraftById(1L);
        verify(aircraftRepository, times(1)).findById(1L);
        assertThat(actualRequest).isEqualTo(doghouse);
    }

    @Test
    @Transactional
    void shouldSaveAircraftToDatabase() {
        var newAircraft = new AircraftDTO("monoplane", snoopy);

        var savedAircraft = aircraftService.saveAircraft(newAircraft);

        assertThat(savedAircraft.getId()).isNotNull();
        var aircraft = aircraftRepository.findById(savedAircraft.getId());
        assertThat(aircraft).isNotNull();
    }

}
