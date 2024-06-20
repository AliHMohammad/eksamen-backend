package dk.almo.backend.DTOs.athlete;

import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.models.Club;
import dk.almo.backend.models.Result;

import java.time.LocalDate;
import java.util.List;

public record AthleteDetailedResponseDTO(
        Long id,
        String firstName,
        String middleName,
        String lastName,
        String fullName,

        String gender,
        LocalDate dateOfBirth,
        int age,
        Club club,
        List<DisciplineResponseDTO> disciplines,
        //TODO: Skal ændres til List DTO-result
        List<Result> results
) {
}
