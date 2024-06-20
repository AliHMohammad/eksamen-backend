package dk.almo.backend.DTOs.athlete;

import dk.almo.backend.DTOs.club.ClubResponseDTO;
import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.models.Club;

import java.time.LocalDate;
import java.util.List;

public record AthleteResponseDTO(
        Long id,
        String firstName,
        String middleName,
        String lastName,
        String fullName,
        String gender,
        LocalDate dateOfBirth,
        int age,
        ClubResponseDTO club,
        List<DisciplineResponseDTO> disciplines




) {

}
