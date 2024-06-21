package dk.almo.backend.services;

import dk.almo.backend.DTOs.result.ResultRequestDTO;
import dk.almo.backend.models.Athlete;
import dk.almo.backend.models.Discipline;
import dk.almo.backend.models.Gender;
import dk.almo.backend.models.ResultType;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.ClubRepository;
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

@ExtendWith(MockitoExtension.class)
class AthleteServiceTest {

    @Mock
    private DisciplineRepository disciplineRepository;
    @Mock
    private AthleteRepository athleteRepository;
    @Mock
    private ClubRepository clubRepository;
    @Mock
    private DisciplineService disciplineService;

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private ClubService clubService;

    @InjectMocks
    private AthleteService athleteService;


    @Test
    void throwsWhenAthleteAlreadyHasDisciplineAssigned() {
        // Arrange
        Discipline discipline = new Discipline(
                "Rowing 1000m",
                ResultType.MILLISECONDS
        );
        discipline.setId(2L);
        Athlete athlete = new Athlete(
                "Fornavn Efternavn",
                LocalDate.now(),
                Gender.MALE,
                null,
                Set.of(discipline)
        );
        athlete.setId(3L);

        Mockito.when(disciplineRepository.findById(discipline.getId())).thenReturn(Optional.of(discipline));
        Mockito.when(athleteRepository.findById(athlete.getId())).thenReturn(Optional.of(athlete));

        assertThrows(BadRequestException.class, () -> athleteService.assignDisciplineToAthlete(discipline.getId(), athlete.getId()));
    }

    @Test
    void throwsWhenDisciplineDoesNotHaveDisciplineAssignedOnDelete() {
        // Arrange
        Discipline discipline = new Discipline(
                "Rowing 1000m",
                ResultType.MILLISECONDS
        );
        discipline.setId(2L);
        Athlete athlete = new Athlete(
                "Fornavn Efternavn",
                LocalDate.now(),
                Gender.MALE,
                null
        );
        athlete.setId(3L);

        Mockito.when(disciplineRepository.findById(discipline.getId())).thenReturn(Optional.of(discipline));
        Mockito.when(athleteRepository.findById(athlete.getId())).thenReturn(Optional.of(athlete));

        assertThrows(BadRequestException.class, () -> athleteService.deleteDisciplineToAthlete(discipline.getId(), athlete.getId()));
    }


}