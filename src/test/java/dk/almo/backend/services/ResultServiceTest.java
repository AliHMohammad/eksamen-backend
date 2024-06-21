package dk.almo.backend.services;

import dk.almo.backend.DTOs.result.ResultDetailedResponseDTO;
import dk.almo.backend.DTOs.result.ResultRequestDTO;
import dk.almo.backend.models.*;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.DisciplineRepository;
import dk.almo.backend.repositories.ResultRepository;
import dk.almo.backend.utils.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ResultServiceTest {

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private DisciplineService disciplineService;

    @Mock
    private AthleteService athleteService;

    @Mock
    private AthleteRepository athleteRepository;

    @InjectMocks
    private ResultService resultService;


    @Test
    void throwWhenAthleteNotAssignedToDiscipline() {
        // Arrange
        ResultRequestDTO resultRequestDTO = new ResultRequestDTO(
               LocalDate.now(),
               1L,
               10L,
               5L
        );
        Athlete athlete = new Athlete(
                "Fornavn Efternavn",
                LocalDate.now(),
                Gender.MALE,
                null
        );
        athlete.setId(resultRequestDTO.athleteId());
        Discipline discipline = new Discipline(
                "Rowing 1000m",
                ResultType.MILLISECONDS
        );
        discipline.setId(resultRequestDTO.disciplineId());

        Mockito.when(disciplineRepository.findById(resultRequestDTO.disciplineId())).thenReturn(Optional.of(discipline));
        Mockito.when(athleteRepository.findById(resultRequestDTO.athleteId())).thenReturn(Optional.of(athlete));


        // Act


        // Assert
        assertThrows(BadRequestException.class, () -> resultService.registerResultForAthlete(resultRequestDTO));
    }

    @Test
    void assignResultWhenAthleteAssignedToDiscipline() {
        // Arrange
        ResultRequestDTO resultRequestDTO = new ResultRequestDTO(
                LocalDate.now(),
                1L,
                10L,
                5L
        );
        Discipline discipline = new Discipline(
                "Rowing 1000m",
                ResultType.MILLISECONDS
        );
        discipline.setId(resultRequestDTO.disciplineId());
        Athlete athlete = new Athlete(
                "Fornavn Efternavn",
                LocalDate.now(),
                Gender.MALE,
                null,
                Set.of(discipline)
        );
        athlete.setId(resultRequestDTO.athleteId());

        Mockito.when(disciplineRepository.findById(resultRequestDTO.disciplineId())).thenReturn(Optional.of(discipline));
        Mockito.when(athleteRepository.findById(resultRequestDTO.athleteId())).thenReturn(Optional.of(athlete));


        ResultDetailedResponseDTO resultDetailedResponseDTO = resultService.registerResultForAthlete(resultRequestDTO);


        assertEquals(resultRequestDTO.value(), resultDetailedResponseDTO.value());
        assertEquals(resultRequestDTO.date(), resultDetailedResponseDTO.date());
    }

    @Test
    void throwWhenAthleteNotAssignedToDisciplineOnUpdate() {
        // Arrange
        long resultId = 5L;
        ResultRequestDTO resultRequestDTO = new ResultRequestDTO(
                LocalDate.now(),
                1L,
                10L,
                5L
        );
        Athlete athlete = new Athlete(
                "Fornavn Efternavn",
                LocalDate.now(),
                Gender.MALE,
                null
        );
        athlete.setId(resultRequestDTO.athleteId());
        Discipline discipline = new Discipline(
                "Rowing 1000m",
                ResultType.MILLISECONDS
        );
        discipline.setId(resultRequestDTO.disciplineId());
        var resultInDb = new Result();
        resultInDb.setId(resultId);

        Mockito.when(resultRepository.findById(resultId)).thenReturn(Optional.of(resultInDb));
        Mockito.when(disciplineRepository.findById(resultRequestDTO.disciplineId())).thenReturn(Optional.of(discipline));
        Mockito.when(athleteRepository.findById(resultRequestDTO.athleteId())).thenReturn(Optional.of(athlete));


        // Act


        // Assert
        assertThrows(BadRequestException.class, () -> resultService.updateResultById(resultId, resultRequestDTO));
    }

}