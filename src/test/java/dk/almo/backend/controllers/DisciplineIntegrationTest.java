package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.discipline.DisciplineRequestDTO;
import dk.almo.backend.DTOs.discipline.DisciplineRequestNameDTO;
import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.models.Discipline;
import dk.almo.backend.models.ResultType;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.DisciplineRepository;
import dk.almo.backend.repositories.ResultRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DisciplineIntegrationTest {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private WebTestClient webTestClient;


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
    void createDiscipline() {
        webTestClient
                .post().uri("/disciplines")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DisciplineRequestDTO(
                        "Test Discipline 300",
                        "MILLIMETER"
                ))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DisciplineResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals("Test Discipline 300", res.name());
                    assertEquals("Millimeter", res.resultType());
                });
    }

    @Test
    void updateDisciplineName() {
        var dis = disciplineRepository.save(new Discipline("Old", ResultType.MILLISECONDS));

        var parameter = "/disciplines/" + dis.getId();

        webTestClient
                .patch().uri(parameter)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DisciplineRequestNameDTO(
                        "New"
                ))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DisciplineResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals("New", res.name());
                });
    }

    @Test
    void getDisciplines() {
        List<Discipline> disciplines = new ArrayList<>();
        disciplines.add(new Discipline("One", ResultType.MILLISECONDS));
        disciplines.add(new Discipline("Two", ResultType.MILLISECONDS));

        disciplineRepository.saveAll(disciplines);

        webTestClient
                .get().uri("/disciplines")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(DisciplineResponseDTO.class)
                .consumeWith(res -> {
                    List<DisciplineResponseDTO> responseBody = res.getResponseBody();
                    assertNotNull(responseBody);
                    assertEquals(2, responseBody.size());
                });
    }


    @Test
    void getDisciplineById() {
        var dis = new Discipline("Rowing 300m", ResultType.MILLISECONDS);

        disciplineRepository.save(dis);

        var parameter = "/disciplines/" + dis.getId();

        webTestClient
                .get().uri(parameter)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DisciplineResponseDTO.class)
                .value(res -> {
                    assertNotNull(res.id());
                    assertEquals(dis.getId(), res.id());
                    assertEquals("Rowing 300m", res.name());
                    assertEquals("Milliseconds", res.resultType());
                });
    }

}