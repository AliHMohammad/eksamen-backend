package dk.almo.backend.DTOs.discipline;

import jakarta.validation.constraints.NotBlank;

public record DisciplineRequestNameDTO(
        //TODO: Mangler besked

        @NotBlank
        String name
) {
}
