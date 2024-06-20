package dk.almo.backend.DTOs.athlete;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record AthleteRequestDTO(
        //TODO: error beskeder

        String firstName,
        String middleName,
        String lastName,
        String fullName,
        @NotBlank
        String gender,
        @NotNull
        LocalDate dateOfBirth,
        Long clubId,
        List<Long> disciplineIds

) {
}
