package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.athlete.AthleteDetailedResponseDTO;
import dk.almo.backend.DTOs.athlete.AthleteRequestDTO;
import dk.almo.backend.DTOs.athlete.AthleteResponseDTO;
import dk.almo.backend.DTOs.discipline.DisciplineRequestDTO;
import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.models.Athlete;
import dk.almo.backend.models.Club;
import dk.almo.backend.models.Gender;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.ClubRepository;
import dk.almo.backend.repositories.DisciplineRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AthleteIntegrationTest {


    //@Autowired
    //private DisciplineRepository disciplineRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private WebTestClient webTestClient;


    @AfterEach
    void deleteEntities() {
        //disciplineRepository.deleteAll();
        athleteRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @Test
    void notNull() {
        assertNotNull(webTestClient);
    }

    @Test
    void createAthlete() {
        var club = new Club("My Club");
        clubRepository.save(club);
        var dob = LocalDate.now();

        webTestClient
                .post().uri("/athletes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AthleteRequestDTO(
                        "Ali",
                        "Haider",
                        "Mohammad",
                        null,
                        "Male",
                        dob,
                        club.getId()
                ))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AthleteResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals("Ali", res.firstName());
                    assertEquals("Haider", res.middleName());
                    assertEquals("Mohammad", res.lastName());
                    assertEquals("Ali Haider Mohammad", res.fullName());
                    assertEquals("Male", res.gender());
                    assertEquals(dob, res.dateOfBirth());
                    assertEquals(club.getId(), res.club().getId());
                    assertEquals(0, res.disciplines().size());
                });
    }


    @Test
    void deleteAthlete() {
        var athlete = new Athlete("Firstname Lastname", LocalDate.now(), Gender.MALE);
        athleteRepository.save(athlete);

        webTestClient
                .delete().uri("/athletes/" + athlete.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AthleteResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals("Firstname Lastname", res.fullName());
                    assertEquals("Male", res.gender());
                });
    }
}