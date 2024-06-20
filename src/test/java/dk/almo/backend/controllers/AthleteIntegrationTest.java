package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.athlete.AthleteDetailedResponseDTO;
import dk.almo.backend.DTOs.athlete.AthletePutRequestDTO;
import dk.almo.backend.DTOs.athlete.AthleteRequestDTO;
import dk.almo.backend.DTOs.athlete.AthleteResponseDTO;
import dk.almo.backend.DTOs.discipline.DisciplineRequestDTO;
import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.models.*;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.ClubRepository;
import dk.almo.backend.repositories.DisciplineRepository;
import dk.almo.backend.repositories.ResultRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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


    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private WebTestClient webTestClient;


    //Note: Rækkefølgen betyder noget. Ellers får du fejl i terminalen
    @AfterEach
    void deleteEntities() {
        resultRepository.deleteAll();
        athleteRepository.deleteAll();
        clubRepository.deleteAll();
        disciplineRepository.deleteAll();
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

    @Test
    void getAthleteById() {
        var athlete = new Athlete("Gabriel Hannah", LocalDate.now(), Gender.FEMALE);
        athleteRepository.save(athlete);

        webTestClient
                .get().uri("/athletes/" + athlete.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AthleteDetailedResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals("Gabriel Hannah", res.fullName());
                    assertEquals("Female", res.gender());
                });
    }

    @Test
    void updateAthleteById() {
        // Arrange
        var athlete = new Athlete("Hannah Montana", LocalDate.now(), Gender.FEMALE);
        athleteRepository.save(athlete);
        var club = new Club("Warriors");
        clubRepository.save(club);
        var payload = new AthletePutRequestDTO(
                "Miley Cyrus",
                "Other",
                club.getId()
        );

        webTestClient
                .put().uri("/athletes/" + athlete.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AthleteResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals(payload.fullName(), res.fullName());
                    assertEquals(payload.gender(), res.gender());
                    assertEquals(payload.clubId(), res.club().getId());
                });
    }

    @Test
    void assignDisciplineToAthlete() {
        // Arrange
        var athlete = new Athlete("Lionel Messi", LocalDate.now(), Gender.MALE);
        athleteRepository.save(athlete);
        var dis = new Discipline("Rowing 500m", ResultType.MILLISECONDS);
        disciplineRepository.save(dis);

        webTestClient
                .post().uri("/athletes/" + athlete.getId() + "/disciplines/" + dis.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AthleteResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals(athlete.getFullName(), res.fullName());
                    assertFalse(res.disciplines().isEmpty());
                });
    }

    @Test
    void deleteDisciplineToAthlete() {
        // Arrange
        var athlete = new Athlete("Mikkel Hansen", LocalDate.now(), Gender.MALE);
        var dis = new Discipline("Rowing 1000m", ResultType.MILLISECONDS);
        athlete.getDisciplines().add(dis);
        dis.getAthletes().add(athlete);
        disciplineRepository.save(dis);
        athleteRepository.save(athlete);

        webTestClient
                .delete().uri("/athletes/" + athlete.getId() + "/disciplines/" + dis.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AthleteResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals(athlete.getFullName(), res.fullName());
                    assertTrue(res.disciplines().isEmpty());
                });
    }
}