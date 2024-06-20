package dk.almo.backend.DTOs.result;

import jakarta.validation.constraints.NotNull;

public record ResultRequestValueDTO(

        @NotNull
        long value
) {

}
