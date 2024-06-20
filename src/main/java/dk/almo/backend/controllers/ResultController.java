package dk.almo.backend.controllers;

import dk.almo.backend.DTOs.result.ResultResponseDTO;
import dk.almo.backend.services.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
