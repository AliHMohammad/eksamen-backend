package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.athlete.AthleteResponseDTO;
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
}