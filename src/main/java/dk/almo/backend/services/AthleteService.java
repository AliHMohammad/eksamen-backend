package dk.almo.backend.services;

import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.models.Athlete;
import dk.almo.backend.models.Discipline;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.DisciplineRepository;
import dk.almo.backend.utils.BadRequestException;
import dk.almo.backend.utils.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AthleteService {


    private final DisciplineRepository disciplineRepository;
    private final AthleteRepository athleteRepository;

    public AthleteService(DisciplineRepository disciplineRepository, AthleteRepository athleteRepository) {
        this.disciplineRepository = disciplineRepository;
        this.athleteRepository = athleteRepository;
    }

    public DisciplineResponseDTO AssignDisciplineToAthlete(long disciplineId, long athleteId) {
        //TODO: Mangler integration test
        //TODO: Lav en unittest?
        Discipline disciplineInDB = disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException("Discipline with id " + disciplineId + " not found."));
        Athlete athleteInDB = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete with id " + athleteId + " not found."));

        // Hvis han alllerede har disciplinen. TODO: unitTest?
        if (athleteInDB.getDisciplines().contains(disciplineInDB)) {
            throw new BadRequestException("Athlete with id " + athleteId + " is already assigned to discipline " + disciplineInDB.getName());
        }

        athleteInDB.getDisciplines().add(disciplineInDB);
        athleteRepository.save(athleteInDB);

        //TODO: RETURN DTO
        return null;
        /*return toDTO(disciplineInDB);*/
    }
}
