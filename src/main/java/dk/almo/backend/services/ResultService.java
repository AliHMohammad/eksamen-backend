package dk.almo.backend.services;

import dk.almo.backend.DTOs.result.ResultRequestDTO;
import dk.almo.backend.DTOs.result.ResultDetailedResponseDTO;
import dk.almo.backend.models.Athlete;
import dk.almo.backend.models.Discipline;
import dk.almo.backend.models.Gender;
import dk.almo.backend.models.Result;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.DisciplineRepository;
import dk.almo.backend.repositories.ResultRepository;
import dk.almo.backend.utils.BadRequestException;
import dk.almo.backend.utils.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public ResultDetailedResponseDTO deleteResultById(long id) {
        Result resultInDB = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + id + " not found."));

        resultRepository.delete(resultInDB);

        return toDTO(resultInDB);
    }

    public ResultDetailedResponseDTO patchResultValue(long id, long newValue) {
        Result resultInDB = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + id + " not found."));
        resultInDB.setValue(newValue);

        resultRepository.save(resultInDB);

        return toDTO(resultInDB);
    }

    public ResultDetailedResponseDTO toDTO(Result result) {
        return new ResultDetailedResponseDTO(
                result.getId(),
                result.getDate(),
                result.getValue(),
                disciplineService.toDTO(result.getDiscipline()),
                athleteService.toDTO(result.getAthlete())
        );
    }

    public Result toEntity(ResultRequestDTO resultRequestDTO) {
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


    public ResultDetailedResponseDTO registerResultForAthlete(ResultRequestDTO resultRequestDTO) {
        Result result = toEntity(resultRequestDTO);

        if (!result.getAthlete().isAssignedToDiscipline(result.getDiscipline())) {
            throw new BadRequestException("Athlete " + result.getAthlete().getFullName() + " is not assigned to " + result.getDiscipline().getName() + ".");
        }

        resultRepository.save(result);

        return toDTO(result);
    }

    public List<ResultDetailedResponseDTO> registerBulkResultsForAthletes(List<ResultRequestDTO> resultRequestDTOList) {
        List<ResultDetailedResponseDTO> resultDetailedResponseDTOS = new ArrayList<>();

        for (ResultRequestDTO request: resultRequestDTOList) {
            resultDetailedResponseDTOS.add(registerResultForAthlete(request));
        }

        return resultDetailedResponseDTOS;
    }

    public Page<ResultDetailedResponseDTO> getResults(
            Integer pageIndex,
            Integer pageSize,
            Long discipline,
            Optional<String> sortDir,
            Optional<String> sortBy,
            Optional<String> gender
    ) {

        Pageable pageable = PageRequest.of(
                pageIndex,
                pageSize,
                Sort.Direction.valueOf(sortDir.orElse("ASC")),
                sortBy.orElse("id")
        );


        if (gender.isPresent()) {
            var genderValue = Gender.valueOf(gender.get().toUpperCase());
            return resultRepository.findAllByAthleteGenderAndDisciplineId(genderValue, discipline, pageable)
                    .map(this::toDTO);
        }

        return resultRepository.findAllByDisciplineId(discipline, pageable)
                .map(this::toDTO);
    }

    public ResultDetailedResponseDTO updateResultById(long id, ResultRequestDTO resultRequestDTO) {
        Result newResult = toEntity(resultRequestDTO);
        Result resultInDB = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + id + " not found."));

        newResult.setId(resultInDB.getId());


        if (!newResult.getAthlete().isAssignedToDiscipline(newResult.getDiscipline())) {
            throw new BadRequestException("Athlete " + newResult.getAthlete().getFullName() + " is not assigned to discipline " + newResult.getDiscipline().getName() + ".");
        }

        resultRepository.save(newResult);

        return toDTO(newResult);
    }
}
