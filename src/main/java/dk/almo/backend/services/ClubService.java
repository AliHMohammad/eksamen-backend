package dk.almo.backend.services;

import dk.almo.backend.DTOs.club.ClubResponseDTO;
import dk.almo.backend.models.Club;
import org.springframework.stereotype.Service;

@Service
public class ClubService {



    public ClubResponseDTO toDTO(Club club) {
        return new ClubResponseDTO(
                club.getId(),
                club.getName()
        );
    }
}
