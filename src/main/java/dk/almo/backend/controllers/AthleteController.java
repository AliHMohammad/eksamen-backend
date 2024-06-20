package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.services.AthleteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "athletes")
public class AthleteController {


    private final AthleteService athleteService;

    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    @PostMapping("/{athleteId}/disciplines/{disciplineId}")
    //TODO: SKAL RETURNERE DTO
    public ResponseEntity<DisciplineResponseDTO> AssignDisciplineToAthlete(@PathVariable long disciplineId, @PathVariable long athleteId) {
        return ResponseEntity.ok(athleteService.AssignDisciplineToAthlete(disciplineId, athleteId));
    }
}
