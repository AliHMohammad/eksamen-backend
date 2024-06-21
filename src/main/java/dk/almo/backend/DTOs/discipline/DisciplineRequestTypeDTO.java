package dk.almo.backend.DTOs.discipline;

import jakarta.validation.constraints.NotBlank;

public record DisciplineRequestTypeDTO(
        //TODO: Mangler besked
        @NotBlank
        String resultType
) {
}
