package dk.almo.backend.DTOs.discipline;

import jakarta.validation.constraints.NotBlank;

public record DisciplineRequestDTO(
        //TODO: Mangler error messages

        @NotBlank
        String name,

        @NotBlank
        String resultType
) {
}
