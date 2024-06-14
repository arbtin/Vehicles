package com.arbtin.vehicles;

import com.arbtin.vehicles.aircraft.Aircraft;
import com.arbtin.vehicles.aircraft.AircraftRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VehiclesApplicationTests {

    @Autowired
    AircraftRepository aircraftRepository;

    private final Aircraft aircraft1 = new Aircraft(
            "biplane",
            "eddie"
//			List.of("fighter", "Recon", "Bomber")
    );

    private final Aircraft aircraft2 = new Aircraft(
            "monoplane",
            "waldo"
//			List.of("fighter", "Recon", "Bomber")
    );

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
    void shouldAllowInsertAndDeleteAircraft() {
        aircraftRepository.save(aircraft1);
        aircraftRepository.save(aircraft2);

        final var aircraft = aircraftRepository.findAll();

        assertThat(aircraft).hasSize(2);

        final var savedObject = aircraftRepository.get(0);

        assertThat(savedObject.getId()).isNotNull();
        assertThat(savedObject.getLocation()).isEqualTo(request0.getLocation());
        assertThat(savedObject.getCallsign()).isEqualTo(request0.getCallsign());

        service.deleteMedevacRequest(request0);
        service.deleteMedevacRequest(request1);

        assertThat(service.findAllMedevacRequests()).hasSize(0);
    }
}