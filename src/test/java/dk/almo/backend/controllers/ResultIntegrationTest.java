package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.athlete.AthleteResponseDTO;
import dk.almo.backend.DTOs.discipline.DisciplineRequestDTO;
import dk.almo.backend.DTOs.discipline.DisciplineRequestNameDTO;
import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.DTOs.result.ResultRequestDTO;
import dk.almo.backend.DTOs.result.ResultRequestValueDTO;
import dk.almo.backend.DTOs.result.ResultResponseDTO;
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
import java.util.Arrays;
import java.util.List;

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
                .expectBody(ResultResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals(result.getDate(), res.date());
                    assertEquals(result.getValue(), res.value());
                    assertEquals(discipline.getResultType(), res.valueType());
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
                .bodyValue(new ResultRequestValueDTO(
                        500L
                ))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResultResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals(500L, res.value());
                });
    }

    @Test
    void registerResultForAthlete() {
        var athlete = new Athlete("Mikkel Jørgensen", LocalDate.now(), Gender.MALE);
        var discipline = new Discipline("Rowing 400m", ResultType.MILLISECONDS);
        athleteRepository.save(athlete);
        disciplineRepository.save(discipline);

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
                .expectBody(ResultResponseDTO.class)
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
        var athOne = new Athlete("Hansen Jensen", LocalDate.now(), Gender.MALE);
        var athTwo = new Athlete("Jesper Mortensen", LocalDate.now(), Gender.MALE);
        athleteRepository.save(athOne);
        athleteRepository.save(athTwo);
        disciplineRepository.save(discipline);

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
                .expectBodyList(ResultResponseDTO.class)
                .consumeWith(res -> {
                    List<ResultResponseDTO> responseBody = res.getResponseBody();
                    assertNotNull(responseBody);
                    assertEquals(2, responseBody.size());
                    assertNotNull(responseBody.get(0).id());
                    assertNotNull(responseBody.get(1).id());
                });
    }
}