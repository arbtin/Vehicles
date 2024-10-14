package com.arbtin.vehicles;

import com.arbtin.vehicles.entity.Aircraft;
import com.arbtin.vehicles.entity.Pilot;
import com.arbtin.vehicles.repository.AircraftRepository;
import com.arbtin.vehicles.service.AircraftService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VehiclesApplicationTests {

    @Autowired
    AircraftRepository aircraftRepository;

    @Autowired
    AircraftService aircraftService;

    Pilot snoopy = new Pilot(1L, "Snoopy", "the Beagle", 10);
    private final Aircraft aircraft1 = new Aircraft(
            "biplane",
            snoopy
//			List.of("fighter", "Recon", "Bomber")
    );

    private final Aircraft aircraft2 = new Aircraft(
            "monoplane",
            snoopy
//			List.of("fighter", "Recon", "Bomber")
    );

/*
    @BeforeEach
    public void setUp() {
        var aircraft1DTO = new AircraftDTO(1L,"monowing", "Bob");
        var aircraft2DTO = new AircraftDTO(2L, "biplane", "Eddie");
    }
*/

    @Test
    void contextLoads() {
    }

    @Test
    void confirmRepositorySetupIsCorrect() {
        // This test can be deleted, but you can use it to confirm everything is working
        // correctly so far without needing to do a ./gradlew bR over and over
        List<Aircraft> all = aircraftRepository.findAll();
        assertThat(all).hasSize(0);
    }

    @Test
    void shouldAllowInsertAndDeleteAircraft(Aircraft aircraft1) {
//        var aircraft1DTO = new AircraftDTO(1L,"monowing", "Bob");
//        var aircraft2DTO = new AircraftDTO(2L, "biplane", "Eddie");

        aircraftService.saveAircraft(aircraft1);
        aircraftService.saveAircraft(aircraft2);

        final var aircraft = aircraftService.findAllAircraft();

        assertThat(aircraft).hasSize(2);

        final var savedObject = aircraftService.findAllAircraft();

        assertThat(aircraft1.getId()).isNotNull();
        assertThat(aircraft1.getAirframe()).isEqualTo("monoplane");
        assertThat(aircraft1.getPilot()).isEqualTo("eddie");

        aircraftService.deleteAircraftById(aircraft1.getId());
        aircraftService.deleteAircraftById(aircraft2.getId());

        assertThat(aircraftService.findAllAircraft()).hasSize(0);
    }
}