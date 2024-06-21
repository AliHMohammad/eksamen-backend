package dk.almo.backend.DTOs.result;

import jakarta.validation.constraints.NotNull;

public record ResultRequestResultTypeDTO(

        @NotNull
        long resultType
) {

}
