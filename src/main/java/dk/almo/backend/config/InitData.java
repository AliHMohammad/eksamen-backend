package dk.almo.backend.config;

import dk.almo.backend.models.*;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.ClubRepository;
import dk.almo.backend.repositories.DisciplineRepository;
import dk.almo.backend.repositories.ResultRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
public class InitData implements ApplicationRunner {


    private final AthleteRepository athleteRepository;
    private final ClubRepository clubRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResultRepository resultRepository;
    List<Discipline> disciplines = new ArrayList<>();
    List<Athlete> athletes = new ArrayList<>();

    public InitData(AthleteRepository athleteRepository, ClubRepository clubRepository, DisciplineRepository disciplineRepository,
                    ResultRepository resultRepository) {
        this.athleteRepository = athleteRepository;
        this.clubRepository = clubRepository;
        this.disciplineRepository = disciplineRepository;
        this.resultRepository = resultRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Creating initial data:");
        createInitialData();
    }

    private void createInitialData() {
        //TODO: Create data
        createAthletes();
        createClubs();
        createDisciplines();
        createAthletesDisciplines();
        createAthletesResults();
    }

    private void createAthletesResults() {
        var result = new Result(LocalDate.now(), 100L, disciplines.get(0), athletes.get(0));
        resultRepository.save(result);
    }

    private void createAthletesDisciplines() {
        athletes.get(0).getDisciplines().add(disciplines.get(0));
        athletes.get(1).getDisciplines().add(disciplines.get(0));
        athleteRepository.saveAll(athletes);
    }

    private void createDisciplines() {
        disciplines.add(new Discipline("Rowing 300m", ResultType.MILLISECONDS));

        disciplineRepository.saveAll(disciplines);
    }

    private void createClubs() {
        List<Club> clubs = new ArrayList<>();

        clubs.add(new Club("Tigers"));

        clubRepository.saveAll(clubs);
    }

    private void createAthletes() {
        athletes.add(new Athlete("ali", "haider", "mohammad", LocalDate.now(), Gender.MALE));
        athletes.add(new Athlete("berfin flORA tuRAN", LocalDate.now(), Gender.FEMALE));

        athleteRepository.saveAll(athletes);

    }

}



