package dk.almo.backend.controllers;


import dk.almo.backend.DTOs.club.ClubResponseDTO;
import dk.almo.backend.services.ClubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "clubs")
public class ClubController {


    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public ResponseEntity<List<ClubResponseDTO>> getClubs() {
        return ResponseEntity.ok(clubService.getClubs());
    }
}
