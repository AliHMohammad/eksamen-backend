package dk.almo.backend.DTOs.result;

import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;

import java.time.LocalDate;

public record ResultResponseDTO(
        Long id,
        LocalDate date,
        long value,
        DisciplineResponseDTO discipline
) {
}
