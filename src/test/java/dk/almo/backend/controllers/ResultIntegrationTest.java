package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.result.ResultRequestDTO;
import dk.almo.backend.DTOs.result.ResultRequestResultTypeDTO;
import dk.almo.backend.DTOs.result.ResultDetailedResponseDTO;
import dk.almo.backend.models.*;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.DisciplineRepository;
import dk.almo.backend.repositories.ResultRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ResultIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;


    @AfterEach
    void deleteEntities() {
        resultRepository.deleteAll();
        athleteRepository.deleteAll();
        disciplineRepository.deleteAll();
    }


    @Test
    void notNull() {
        assertNotNull(webTestClient);
    }


    @Test
    void deleteResultById() {
        // Arrange
        var athlete = new Athlete("Mikkel Jørgensen", LocalDate.now(), Gender.MALE);
        var discipline = new Discipline("Rowing 400m", ResultType.MILLISECONDS);
        athleteRepository.save(athlete);
        disciplineRepository.save(discipline);
        var date = LocalDate.now();
        var result = new Result(date, 100L, discipline, athlete);
        resultRepository.save(result);

        webTestClient
                .delete().uri("/results/" + result.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResultDetailedResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals(result.getDate(), res.date());
                    assertEquals(result.getValue(), res.value());
                });

    }

    @Test
    void updateResultValue() {
        var athlete = new Athlete("Mikkel Jørgensen", LocalDate.now(), Gender.MALE);
        var discipline = new Discipline("Rowing 400m", ResultType.MILLISECONDS);
        athleteRepository.save(athlete);
        disciplineRepository.save(discipline);
        var result = new Result(null, 200L, discipline, athlete);
        resultRepository.save(result);

        webTestClient
                .patch().uri("/results/" + result.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ResultRequestResultTypeDTO(
                        500L
                ))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResultDetailedResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals(500L, res.value());
                });
    }

    @Test
    void registerResultForAthlete() {
        var discipline = new Discipline("Rowing 400m", ResultType.MILLISECONDS);
        disciplineRepository.save(discipline);
        var athlete = new Athlete("Mikkel Jørgensen", LocalDate.now(), Gender.MALE, null, Set.of(discipline));
        athleteRepository.save(athlete);

        var payload = new ResultRequestDTO(
                LocalDate.now(),
                200L,
                discipline.getId(),
                athlete.getId()
        );


        webTestClient
                .post().uri("/results")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResultDetailedResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals(payload.value(), res.value());
                    assertEquals(payload.date(), res.date());
                    assertEquals(payload.athleteId(), res.athlete().id());
                    assertEquals(payload.disciplineId(), res.discipline().id());
                });
    }


    @Test
    void registerBulkResultsForAthletes() {
        var discipline = new Discipline("Rowing 400m", ResultType.MILLISECONDS);
        disciplineRepository.save(discipline);
        var athOne = new Athlete("Hansen Jensen", LocalDate.now(), Gender.MALE, null, Set.of(discipline));
        var athTwo = new Athlete("Jesper Mortensen", LocalDate.now(), Gender.MALE, null, Set.of(discipline));
        athleteRepository.save(athOne);
        athleteRepository.save(athTwo);

        var payloadOne = new ResultRequestDTO(
                LocalDate.now().plusDays(4),
                200L,
                discipline.getId(),
                athOne.getId()
        );

        var payloadTwo = new ResultRequestDTO(
                LocalDate.now().plusDays(2),
                600L,
                discipline.getId(),
                athTwo.getId()
        );

        List<ResultRequestDTO> payload = new ArrayList<>(List.of(payloadOne, payloadTwo));


        webTestClient
                .post().uri("/results/bulk")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ResultDetailedResponseDTO.class)
                .consumeWith(res -> {
                    List<ResultDetailedResponseDTO> responseBody = res.getResponseBody();
                    assertNotNull(responseBody);
                    assertEquals(2, responseBody.size());
                    assertNotNull(responseBody.get(0).id());
                    assertNotNull(responseBody.get(1).id());
                });
    }

    @Test
    void updateResultById() {
        var discipline = new Discipline("Rowing 400m", ResultType.MILLISECONDS);
        var newDiscipline = new Discipline("Skiing 200m", ResultType.MILLISECONDS);
        disciplineRepository.save(discipline);
        disciplineRepository.save(newDiscipline);
        var athlete = new Athlete("Mikkel Jørgensen", LocalDate.now(), Gender.MALE, null, Set.of(discipline, newDiscipline));
        athleteRepository.save(athlete);
        var oldResult = new Result(LocalDate.now(), 100L, discipline, athlete);
        resultRepository.save(oldResult);

        var payload = new ResultRequestDTO(
                LocalDate.now().plusDays(2),
                2000L,
                newDiscipline.getId(),
                athlete.getId()
        );

        webTestClient
                .put().uri("/results/" + oldResult.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResultDetailedResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals(payload.date(), res.date());
                    assertEquals(payload.value(), res.value());
                    assertEquals(payload.disciplineId(), res.discipline().id());
                    assertEquals(payload.athleteId(), res.athlete().id());
                });

    }
}