package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.athlete.AthleteDetailedResponseDTO;
import dk.almo.backend.DTOs.athlete.AthleteRequestDTO;
import dk.almo.backend.DTOs.athlete.AthleteResponseDTO;
import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.services.AthleteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "athletes")
public class AthleteController {


    private final AthleteService athleteService;

    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }


    @PostMapping
    public ResponseEntity<AthleteResponseDTO> createAthlete(@Valid @RequestBody AthleteRequestDTO athleteRequestDTO) {
        AthleteResponseDTO athleteResponseDTO = athleteService.createAthlete(athleteRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(athleteResponseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(athleteResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AthleteResponseDTO> deleteAthlete(@PathVariable long id) {
        return ResponseEntity.ok(athleteService.deleteAthlete(id));
    }


    @PostMapping("/{athleteId}/disciplines/{disciplineId}")
    //TODO: SKAL RETURNERE DTO
    public ResponseEntity<DisciplineResponseDTO> AssignDisciplineToAthlete(@PathVariable long disciplineId, @PathVariable long athleteId) {
        return ResponseEntity.ok(athleteService.AssignDisciplineToAthlete(disciplineId, athleteId));
    }
}
