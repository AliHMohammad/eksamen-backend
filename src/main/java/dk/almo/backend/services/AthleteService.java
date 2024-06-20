package dk.almo.backend.services;

import dk.almo.backend.DTOs.athlete.AthleteDetailedResponseDTO;
import dk.almo.backend.DTOs.athlete.AthletePutRequestDTO;
import dk.almo.backend.DTOs.athlete.AthleteRequestDTO;
import dk.almo.backend.DTOs.athlete.AthleteResponseDTO;
import dk.almo.backend.DTOs.result.ResultDetailedResponseDTO;
import dk.almo.backend.DTOs.result.ResultResponseDTO;
import dk.almo.backend.models.Athlete;
import dk.almo.backend.models.Club;
import dk.almo.backend.models.Discipline;
import dk.almo.backend.models.Gender;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.ClubRepository;
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
public class AthleteService {


    private final DisciplineRepository disciplineRepository;
    private final AthleteRepository athleteRepository;
    private final ClubRepository clubRepository;
    private final DisciplineService disciplineService;
    private final ResultRepository resultRepository;

    public AthleteService(DisciplineRepository disciplineRepository, AthleteRepository athleteRepository, ClubRepository clubRepository,
                          DisciplineService disciplineService, ResultRepository resultRepository) {
        this.disciplineRepository = disciplineRepository;
        this.athleteRepository = athleteRepository;
        this.clubRepository = clubRepository;
        this.disciplineService = disciplineService;
        this.resultRepository = resultRepository;

    }


    public AthleteResponseDTO createAthlete(AthleteRequestDTO athleteRequestDTO) {
        Athlete athlete = toEntity(athleteRequestDTO);
        athleteRepository.save(athlete);
        return toDTO(athlete);
    }

    public AthleteResponseDTO deleteAthlete(long id) {
        //TODO: Unit Test?
        Athlete athleteInDB = athleteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Athlete with id " + id + " not found."));

        athleteRepository.deleteById(id);
        return toDTO(athleteInDB);
    }

    public AthleteDetailedResponseDTO getAthleteById(long id) {
        return athleteRepository.findById(id).map(this::toDetailedDTO)
                .orElseThrow(() -> new EntityNotFoundException("Athlete with id " + id + " not found."));
    }

    public AthleteResponseDTO updateAthleteById(long id, AthletePutRequestDTO athleteRequestDTO) {
        Athlete athleteInDB = athleteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Athlete with id " + id + " not found."));

        Club clubInDB = clubRepository.findById(athleteRequestDTO.clubId())
                .orElseThrow(() -> new EntityNotFoundException("Club with id " + athleteRequestDTO.clubId() + " not found."));

        athleteInDB.setFullName(athleteRequestDTO.fullName());
        athleteInDB.setGender(athleteRequestDTO.gender());
        athleteInDB.setClub(clubInDB);

        athleteRepository.save(athleteInDB);
        return toDTO(athleteInDB);
    }


    public AthleteResponseDTO assignDisciplineToAthlete(long disciplineId, long athleteId) {
        //TODO: Lav en unittest?
        Discipline disciplineInDB = disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException("Discipline with id " + disciplineId + " not found."));
        Athlete athleteInDB = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete with id " + athleteId + " not found."));

        // Hvis han alllerede har disciplinen. TODO: unitTest?
        if (athleteInDB.getDisciplines().contains(disciplineInDB)) {
            throw new BadRequestException("Athlete with id " + athleteId + " is already assigned to discipline " + disciplineInDB.getName() + ".");
        }

        athleteInDB.getDisciplines().add(disciplineInDB);
        disciplineInDB.getAthletes().add(athleteInDB);
        athleteRepository.save(athleteInDB);
        disciplineRepository.save(disciplineInDB);


        return toDTO(athleteInDB);
    }

    public AthleteResponseDTO deleteDisciplineToAthlete(long disciplineId, long athleteId) {
        //TODO: Lav en unittest?
        Discipline disciplineInDB = disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException("Discipline with id " + disciplineId + " not found."));
        Athlete athleteInDB = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete with id " + athleteId + " not found."));

        // Hvis han alllerede har disciplinen. TODO: unitTest?
        if (!athleteInDB.getDisciplines().contains(disciplineInDB)) {
            throw new BadRequestException("Athlete with id " + athleteId + " is not assigned to discipline " + disciplineInDB.getName() + ".");
        }

        athleteInDB.getDisciplines().remove(disciplineInDB);
        disciplineInDB.getAthletes().remove(athleteInDB);
        athleteRepository.save(athleteInDB);
        disciplineRepository.save(disciplineInDB);


        return toDTO(athleteInDB);
    }

    private Athlete toEntity(AthleteRequestDTO athleteRequestDTO) {
        Athlete athlete = new Athlete(
                athleteRequestDTO.firstName(),
                athleteRequestDTO.middleName(),
                athleteRequestDTO.lastName(),
                athleteRequestDTO.dateOfBirth(),
                Gender.valueOf(athleteRequestDTO.gender().toUpperCase()),
                clubRepository.findById(athleteRequestDTO.clubId())
                        .orElseThrow(() -> new EntityNotFoundException("Club with id " + athleteRequestDTO.clubId() + " not found."))
        );

        if (athleteRequestDTO.fullName() != null) {
            athlete.setFullName(athleteRequestDTO.fullName());
        }

        if (athleteRequestDTO.fullName() == null && athlete.getFullName() == null) {
            throw new BadRequestException("Name for athlete is missing");
        }

        return athlete;
    }

    public AthleteResponseDTO toDTO(Athlete athlete) {

        return new AthleteResponseDTO(
                athlete.getId(),
                athlete.getFirstName(),
                athlete.getMiddleName(),
                athlete.getLastName(),
                athlete.getFullName(),
                athlete.getGender(),
                athlete.getDateOfBirth(),
                athlete.getAge(),
                athlete.getClub(),
                athlete.getDisciplines().isEmpty() ? new ArrayList<>() : athlete.getDisciplines().stream().map(disciplineService::toDTO).toList()
        );
    }

    private AthleteDetailedResponseDTO toDetailedDTO(Athlete athlete) {
        List<ResultResponseDTO> results = resultRepository.findAllByAthleteId(athlete.getId()).stream().map(result ->
                new ResultResponseDTO(
                        result.getId(),
                        result.getDate(),
                        result.getValue(),
                        disciplineService.toDTO(result.getDiscipline())
                )
        ).toList();

        return new AthleteDetailedResponseDTO(
                athlete.getId(),
                athlete.getFirstName(),
                athlete.getMiddleName(),
                athlete.getLastName(),
                athlete.getFullName(),
                athlete.getGender(),
                athlete.getDateOfBirth(),
                athlete.getAge(),
                athlete.getClub(),
                athlete.getDisciplines().isEmpty() ? new ArrayList<>() : athlete.getDisciplines().stream().map(disciplineService::toDTO).toList(),
                results
        );
    }

    public Page<AthleteResponseDTO> getAthletes(
            Integer pageIndex,
            Integer pageSize,
            Long discipline,
            Optional<String> sortDir,
            Optional<String> sortBy,
            Optional<String> gender,
            Optional<String> club,
            Optional<String> searchBy) {

        Pageable pageable = PageRequest.of(
                pageIndex,
                pageSize,
                Sort.Direction.valueOf(sortDir.orElse("ASC")),
                sortBy.orElse("id")
        );


        if (searchBy.isPresent() && gender.isPresent() && club.isPresent()) {
            var searchValue = searchBy.get();
            var genderValue = Gender.valueOf(gender.get().toUpperCase());
            var clubId = Long.valueOf(club.get());
            return athleteRepository.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseAndGenderAndClubIdAndDisciplinesId(
                            searchValue, searchValue, genderValue, clubId, discipline, pageable)
                    .map(this::toDTO);
        }

        if (searchBy.isPresent() && gender.isPresent()) {
            var searchValue = searchBy.get();
            var genderValue = Gender.valueOf(gender.get().toUpperCase());
            return athleteRepository.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseAndGenderAndDisciplinesId(searchValue, searchValue,
                            genderValue, discipline, pageable)
                    .map(this::toDTO);
        }

        if (searchBy.isPresent() && club.isPresent()) {
            var searchValue = searchBy.get();
            var clubId = Long.valueOf(club.get());
            return athleteRepository.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseAndClubIdAndDisciplinesId(searchValue, searchValue,
                            clubId, discipline, pageable)
                    .map(this::toDTO);
        }

        if (gender.isPresent() && club.isPresent()) {
            var genderValue = Gender.valueOf(gender.get().toUpperCase());
            var clubId = Long.valueOf(club.get());
            return athleteRepository.findAllByGenderAndClubIdAndDisciplinesId(genderValue, clubId, discipline, pageable)
                    .map(this::toDTO);
        }


        if (searchBy.isPresent()) {
            var searchValue = searchBy.get();
            return athleteRepository.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseAndDisciplinesId(searchValue, searchValue, discipline,
                            pageable)
                    .map(this::toDTO);
        }

        if (gender.isPresent()) {
            var genderValue = Gender.valueOf(gender.get().toUpperCase());
            return athleteRepository.findAllByGenderAndDisciplinesId(genderValue, discipline, pageable)
                    .map(this::toDTO);
        }

        if (club.isPresent()) {
            var clubId = Long.valueOf(club.get());
            return athleteRepository.findAllByClubIdAndDisciplinesId(clubId, discipline, pageable)
                    .map(this::toDTO);
        }

        return athleteRepository.findAllByDisciplinesId(discipline, pageable)
                .map(this::toDTO);


    }
}
