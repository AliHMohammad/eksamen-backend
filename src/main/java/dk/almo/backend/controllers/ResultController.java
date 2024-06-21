package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.result.ResultRequestDTO;
import dk.almo.backend.DTOs.result.ResultRequestValueDTO;
import dk.almo.backend.DTOs.result.ResultDetailedResponseDTO;
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
    public ResponseEntity<Page<ResultDetailedResponseDTO>> getResults(
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
    public ResponseEntity<ResultDetailedResponseDTO> deleteResultById(@PathVariable long id) {
        return ResponseEntity.ok(resultService.deleteResultById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultDetailedResponseDTO> patchResultValue(@Valid @RequestBody ResultRequestValueDTO resultRequestValueDTO, @PathVariable long id) {
        return ResponseEntity.ok(resultService.patchResultValue(id, resultRequestValueDTO.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDetailedResponseDTO> updateResultById(@Valid @RequestBody ResultRequestDTO resultRequestDTO, @PathVariable long id) {
        return ResponseEntity.ok(resultService.updateResultById(id, resultRequestDTO));
    }

    @PostMapping
    public ResponseEntity<ResultDetailedResponseDTO> registerResultForAthlete(@Valid @RequestBody ResultRequestDTO resultRequestDTO) {
        ResultDetailedResponseDTO resultDetailedResponseDTO = resultService.registerResultForAthlete(resultRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resultDetailedResponseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(resultDetailedResponseDTO);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ResultDetailedResponseDTO>> registerBulkResultsForAthletes(@Valid @RequestBody List<ResultRequestDTO> resultRequestDTOList){
        List<ResultDetailedResponseDTO> resultDetailedResponseDTO = resultService.registerBulkResultsForAthletes(resultRequestDTOList);
        return ResponseEntity.ok(resultDetailedResponseDTO);
    }
}
