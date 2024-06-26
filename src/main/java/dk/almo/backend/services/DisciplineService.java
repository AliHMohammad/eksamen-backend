package dk.almo.backend.services;

import dk.almo.backend.DTOs.discipline.DisciplineRequestDTO;
import dk.almo.backend.DTOs.discipline.DisciplineResponseDTO;
import dk.almo.backend.models.Discipline;
import dk.almo.backend.models.ResultType;
import dk.almo.backend.repositories.DisciplineRepository;
import dk.almo.backend.utils.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public DisciplineResponseDTO updateDisciplineType(long id, String type) {
        Discipline disciplineInDB = disciplineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discipline with id " + id + " not found."));

        disciplineInDB.setResultType(type);
        disciplineRepository.save(disciplineInDB);

        return toDTO(disciplineInDB);
    }


    public List<DisciplineResponseDTO> getDisciplines() {
        return disciplineRepository.findAll().stream().map(this::toDTO).toList();
    }

    public DisciplineResponseDTO getDisciplineById(long id) {
        return disciplineRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Discipline with id " + id + " not found."));
    }


    private Discipline toEntity(DisciplineRequestDTO disciplineRequestDTO) {
        return new Discipline(
                disciplineRequestDTO.name(),
                ResultType.valueOf(disciplineRequestDTO.resultType().toUpperCase())
        );
    }

    public DisciplineResponseDTO toDTO(Discipline discipline) {

        return new DisciplineResponseDTO(
                discipline.getId(),
                discipline.getName(),
                discipline.getResultType()
        );
    }
}
