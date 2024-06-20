package dk.almo.backend.DTOs.result;

import jakarta.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDate;

public record ResultRequestDTO(
        //TODO: Error messages
        @NotNull
        LocalDate date,

        @NotNull
        long value,

        @NotNull
        long disciplineId,

        @NotNull
        long athleteId
) {
}
