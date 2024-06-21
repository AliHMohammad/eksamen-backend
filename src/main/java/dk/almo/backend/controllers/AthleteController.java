package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.athlete.AthleteDetailedResponseDTO;
import dk.almo.backend.DTOs.athlete.AthletePutRequestDTO;
import dk.almo.backend.DTOs.athlete.AthleteRequestDTO;
import dk.almo.backend.DTOs.athlete.AthleteResponseDTO;
import dk.almo.backend.DTOs.result.ResultRequestDTO;
import dk.almo.backend.services.AthleteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "athletes")
public class AthleteController {


    private final AthleteService athleteService;

    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }


    @GetMapping
    public ResponseEntity<Page<AthleteResponseDTO>> getAthletes(
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize,
            @RequestParam String discipline,
            @RequestParam Optional<String> sortDir,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<String> gender,
            @RequestParam Optional<String> club,
            @RequestParam Optional<String> searchBy
    ) {
        return ResponseEntity.ok(athleteService.getAthletes(pageIndex, pageSize, Long.valueOf(discipline), sortDir, sortBy, gender, club, searchBy));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AthleteResponseDTO>> getAthletes() {
        return ResponseEntity.ok(athleteService.getAthletes());
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

    @GetMapping("/{id}")
    public ResponseEntity<AthleteDetailedResponseDTO> getAthleteById(@PathVariable long id) {
        return ResponseEntity.ok(athleteService.getAthleteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AthleteResponseDTO> updateAthleteById(@PathVariable long id, @Valid @RequestBody AthletePutRequestDTO athleteRequestDTO) {
        return ResponseEntity.ok(athleteService.updateAthleteById(id, athleteRequestDTO));
    }

    @PostMapping("/{athleteId}/disciplines/{disciplineId}")
    public ResponseEntity<AthleteResponseDTO> assignDisciplineToAthlete(@PathVariable long disciplineId, @PathVariable long athleteId) {
        return ResponseEntity.ok(athleteService.assignDisciplineToAthlete(disciplineId, athleteId));
    }

    @DeleteMapping("/{athleteId}/disciplines/{disciplineId}")
    public ResponseEntity<AthleteResponseDTO> deleteDisciplineToAthlete(@PathVariable long disciplineId, @PathVariable long athleteId) {
        return ResponseEntity.ok(athleteService.deleteDisciplineToAthlete(disciplineId, athleteId));
    }


}
