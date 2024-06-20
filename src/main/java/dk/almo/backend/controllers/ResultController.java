package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.result.ResultRequestValueDTO;
import dk.almo.backend.DTOs.result.ResultResponseDTO;
import dk.almo.backend.services.ResultService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "results")
public class ResultController {


    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponseDTO> deleteResultById(@PathVariable long id) {
        return ResponseEntity.ok(resultService.deleteResultById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultResponseDTO> updateResultValue(@Valid @RequestBody ResultRequestValueDTO resultRequestValueDTO, @PathVariable long id) {
        return ResponseEntity.ok(resultService.updateResultValue(id, resultRequestValueDTO.value()));
    }
}
