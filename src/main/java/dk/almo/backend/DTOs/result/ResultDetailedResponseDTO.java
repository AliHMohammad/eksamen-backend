package dk.almo.backend.DTOs.result;

import dk.almo.backend.DTOs.athlete.AthleteResponseDTO;
import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;

import java.time.LocalDate;

public record ResultDetailedResponseDTO(
        Long id,
        LocalDate date,
        long value,
        DisciplineResponseDTO discipline,
        AthleteResponseDTO athlete

) {

}
