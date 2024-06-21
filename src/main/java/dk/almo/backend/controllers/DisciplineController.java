package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.discipline.DisciplineRequestDTO;
import dk.almo.backend.DTOs.discipline.DisciplineRequestTypeDTO;
import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.services.DisciplineService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "disciplines")
public class DisciplineController {


    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }


    @PostMapping
    public ResponseEntity<DisciplineResponseDTO> createDiscipline(@Valid @RequestBody DisciplineRequestDTO disciplineRequestDTO) {
        DisciplineResponseDTO disciplineResponseDTO = disciplineService.createDiscipline(disciplineRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(disciplineResponseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(disciplineResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DisciplineResponseDTO> updateDisciplineType(@PathVariable long id, @Valid @RequestBody DisciplineRequestTypeDTO disciplineRequestNameDTO) {
        return ResponseEntity.ok(disciplineService.updateDisciplineType(id, disciplineRequestNameDTO.resultType()));
    }

    @GetMapping
    public ResponseEntity<List<DisciplineResponseDTO>> getDisciplines() {
        return ResponseEntity.ok(disciplineService.getDisciplines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineResponseDTO> getDisciplineById(@PathVariable long id) {
        return ResponseEntity.ok(disciplineService.getDisciplineById(id));
    }


}
