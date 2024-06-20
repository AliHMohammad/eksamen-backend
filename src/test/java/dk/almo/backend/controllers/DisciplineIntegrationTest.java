package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.discipline.DisciplineRequestDTO;
import dk.almo.backend.DTOs.discipline.DisciplineRequestNameDTO;
import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.models.Discipline;
import dk.almo.backend.models.ResultType;
import dk.almo.backend.repositories.DisciplineRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DisciplineIntegrationTest {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private WebTestClient webTestClient;


    @AfterEach
    void deleteEntities() {
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
                    assertEquals("MILLIMETER", res.resultType());
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
}