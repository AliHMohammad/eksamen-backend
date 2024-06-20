package dk.almo.backend.DTOs.athlete;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AthleteRequestDTO(
        //TODO: error beskeder

        @NotBlank
        String firstName,
        String middleName,
        @NotBlank
        String lastName,
        String fullName,
        @NotBlank
        String gender,
        @NotNull
        LocalDate dateOfBirth,
        Long clubId

) {
}
