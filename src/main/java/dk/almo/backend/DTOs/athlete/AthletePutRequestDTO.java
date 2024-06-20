package dk.almo.backend.DTOs.athlete;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AthletePutRequestDTO(
        //TODO: Fejl beskeder
        @NotBlank
        String fullName,
        @NotBlank
        String gender,
        @NotNull
        Long clubId,

        @NotNull
        List<Long> disciplineIds
) {
}
