package dk.almo.backend.services;

import dk.almo.backend.DTOs.result.ResultRequestDTO;
import dk.almo.backend.DTOs.result.ResultResponseDTO;
import dk.almo.backend.models.Athlete;
import dk.almo.backend.models.Discipline;
import dk.almo.backend.models.Result;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.DisciplineRepository;
import dk.almo.backend.repositories.ResultRepository;
import dk.almo.backend.utils.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {


    private final ResultRepository resultRepository;
    private final DisciplineRepository disciplineRepository;
    private final DisciplineService disciplineService;
    private final AthleteService athleteService;
    private final AthleteRepository athleteRepository;

    public ResultService(ResultRepository resultRepository, DisciplineRepository disciplineRepository, DisciplineService disciplineService,
                         AthleteService athleteService, AthleteRepository athleteRepository) {
        this.resultRepository = resultRepository;
        this.disciplineRepository = disciplineRepository;
        this.disciplineService = disciplineService;
        this.athleteService = athleteService;
        this.athleteRepository = athleteRepository;
    }


    public ResultResponseDTO deleteResultById(long id) {
        Result resultInDB = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + id + " not found."));

        resultRepository.delete(resultInDB);

        return toDTO(resultInDB);
    }

    public ResultResponseDTO updateResultValue(long id, long newValue) {
        Result resultInDB = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + id + " not found."));
        resultInDB.setValue(newValue);

        resultRepository.save(resultInDB);

        return toDTO(resultInDB);
    }

    private ResultResponseDTO toDTO(Result result) {
        return new ResultResponseDTO(
                result.getId(),
                result.getDate(),
                result.getValue(),
                result.getDiscipline().getResultType(),
                disciplineService.toDTO(result.getDiscipline()),
                athleteService.toDTO(result.getAthlete())
        );
    }

    private Result toEntity(ResultRequestDTO resultRequestDTO) {
        Discipline disciplineInDB = disciplineRepository.findById(resultRequestDTO.disciplineId())
                .orElseThrow(() -> new EntityNotFoundException("Discipline with id " + resultRequestDTO.disciplineId() + " not found."));
        Athlete athleteInDB = athleteRepository.findById(resultRequestDTO.athleteId())
                .orElseThrow(() -> new EntityNotFoundException("Athlete with id " + resultRequestDTO.athleteId() + " not found."));

        return new Result(
                resultRequestDTO.date(),
                resultRequestDTO.value(),
                disciplineInDB,
                athleteInDB
        );
    }


    public ResultResponseDTO registerResultForAthlete(ResultRequestDTO resultRequestDTO) {
        Result result = toEntity(resultRequestDTO);

        resultRepository.save(result);

        return toDTO(result);
    }

    public List<ResultResponseDTO> registerBulkResultsForAthletes(List<ResultRequestDTO> resultRequestDTOList) {
        List<ResultResponseDTO> resultResponseDTOS = new ArrayList<>();



        for (ResultRequestDTO request: resultRequestDTOList) {
            resultResponseDTOS.add(registerResultForAthlete(request));
        }

        return resultResponseDTOS;
    }
}
