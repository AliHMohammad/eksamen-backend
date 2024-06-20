package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.athlete.AthleteDetailedResponseDTO;
import dk.almo.backend.DTOs.result.ResultRequestDTO;
import dk.almo.backend.DTOs.result.ResultRequestValueDTO;
import dk.almo.backend.DTOs.result.ResultResponseDTO;
import dk.almo.backend.services.ResultService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "results")
public class ResultController {


    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }


    @GetMapping
    public ResponseEntity<Page<ResultResponseDTO>> getResults(
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize,
            @RequestParam String discipline,
            @RequestParam Optional<String> sortDir,
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<String> gender
    ) {
        return ResponseEntity.ok(resultService.getResults(pageIndex, pageSize, Long.valueOf(discipline), sortDir, sortBy, gender));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponseDTO> deleteResultById(@PathVariable long id) {
        return ResponseEntity.ok(resultService.deleteResultById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultResponseDTO> updateResultValue(@Valid @RequestBody ResultRequestValueDTO resultRequestValueDTO, @PathVariable long id) {
        return ResponseEntity.ok(resultService.updateResultValue(id, resultRequestValueDTO.value()));
    }

    @PostMapping
    public ResponseEntity<ResultResponseDTO> registerResultForAthlete(@Valid @RequestBody ResultRequestDTO resultRequestDTO) {
        ResultResponseDTO resultResponseDTO = resultService.registerResultForAthlete(resultRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resultResponseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(resultResponseDTO);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ResultResponseDTO>> registerBulkResultsForAthletes(@Valid @RequestBody List<ResultRequestDTO> resultRequestDTOList){
        List<ResultResponseDTO> resultResponseDTO = resultService.registerBulkResultsForAthletes(resultRequestDTOList);
        return ResponseEntity.ok(resultResponseDTO);
    }
}
