package dk.almo.backend.services;

import dk.almo.backend.DTOs.discipline.DisciplineRequestDTO;
import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.models.Discipline;
import dk.almo.backend.models.ResultType;
import dk.almo.backend.repositories.DisciplineRepository;
import org.springframework.stereotype.Service;

@Service
public class DisciplineService {


    private final DisciplineRepository disciplineRepository;


    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;

    }


    public DisciplineResponseDTO createDiscipline(DisciplineRequestDTO disciplineRequestDTO) {
        Discipline discipline = toEntity(disciplineRequestDTO);
        disciplineRepository.save(discipline);
        return toDTO(discipline);
    }


    private Discipline toEntity(DisciplineRequestDTO disciplineRequestDTO) {
        return new Discipline(
                disciplineRequestDTO.name(),
                ResultType.valueOf(disciplineRequestDTO.resultType().toUpperCase())
        );
    }

    private DisciplineResponseDTO toDTO(Discipline discipline) {
        return new DisciplineResponseDTO(
                discipline.getId(),
                discipline.getName(),
                discipline.getResultType().toString()
        );
    }
}
