package dk.almo.backend.services;

import dk.almo.backend.DTOs.club.ClubResponseDTO;
import dk.almo.backend.models.Club;
import dk.almo.backend.repositories.ClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {


    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public List<ClubResponseDTO> getClubs() {
        return clubRepository.findAll().stream().map(this::toDTO).toList();
    }

    public ClubResponseDTO toDTO(Club club) {
        return new ClubResponseDTO(
                club.getId(),
                club.getName()
        );
    }


}
