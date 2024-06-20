package dk.almo.backend.config;

import dk.almo.backend.models.*;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.ClubRepository;
import dk.almo.backend.repositories.DisciplineRepository;
import dk.almo.backend.repositories.ResultRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
    List<Club> clubs = new ArrayList<>();

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
        createClubs();
        createDisciplines();
        createAthletes();
        createAthletesDisciplines();
        createAthletesResults();
    }

    private void createAthletesResults() {
        List<Result> results = new ArrayList<>();
        results.add(new Result(LocalDate.now(), 100L, disciplines.get(0), athletes.get(0)));
        results.add(new Result(LocalDate.now(), 150L, disciplines.get(1), athletes.get(1)));
        results.add(new Result(LocalDate.now(), 200L, disciplines.get(2), athletes.get(2)));
        results.add(new Result(LocalDate.now(), 300L, disciplines.get(3), athletes.get(3)));
        results.add(new Result(LocalDate.now(), 110L, disciplines.get(4), athletes.get(4)));
        results.add(new Result(LocalDate.now(), 160L, disciplines.get(5), athletes.get(5)));
        results.add(new Result(LocalDate.now(), 210L, disciplines.get(6), athletes.get(6)));
        results.add(new Result(LocalDate.now(), 310L, disciplines.get(7), athletes.get(7)));
        results.add(new Result(LocalDate.now(), 120L, disciplines.get(8), athletes.get(8)));
        results.add(new Result(LocalDate.now(), 170L, disciplines.get(9), athletes.get(9)));
        results.add(new Result(LocalDate.now(), 220L, disciplines.get(10), athletes.get(10)));
        results.add(new Result(LocalDate.now(), 320L, disciplines.get(11), athletes.get(11)));
        resultRepository.saveAll(results);
    }

    private void createAthletesDisciplines() {
        athletes.get(0).getDisciplines().add(disciplines.get(0));
        athletes.get(0).getDisciplines().add(disciplines.get(1));
        athletes.get(1).getDisciplines().add(disciplines.get(2));
        athletes.get(1).getDisciplines().add(disciplines.get(3));
        athletes.get(2).getDisciplines().add(disciplines.get(4));
        athletes.get(2).getDisciplines().add(disciplines.get(5));
        athletes.get(3).getDisciplines().add(disciplines.get(6));
        athletes.get(3).getDisciplines().add(disciplines.get(7));
        athletes.get(4).getDisciplines().add(disciplines.get(8));
        athletes.get(4).getDisciplines().add(disciplines.get(9));
        athletes.get(5).getDisciplines().add(disciplines.get(10));
        athletes.get(5).getDisciplines().add(disciplines.get(11));
        athleteRepository.saveAll(athletes);
    }

    private void createDisciplines() {
        disciplines.add(new Discipline("Rowing 300m", ResultType.MILLISECONDS));
        disciplines.add(new Discipline("Long Jump", ResultType.CENTIMETER));
        disciplines.add(new Discipline("Shot Put", ResultType.MILLIMETER));
        disciplines.add(new Discipline("High Jump", ResultType.CENTIMETER));
        disciplines.add(new Discipline("100m Sprint", ResultType.MILLISECONDS));
        disciplines.add(new Discipline("200m Sprint", ResultType.MILLISECONDS));
        disciplines.add(new Discipline("Marathon", ResultType.MILLISECONDS));
        disciplines.add(new Discipline("Discus Throw", ResultType.CENTIMETER));
        disciplines.add(new Discipline("Javelin Throw", ResultType.CENTIMETER));
        disciplines.add(new Discipline("Pole Vault", ResultType.CENTIMETER));
        disciplines.add(new Discipline("Hammer Throw", ResultType.MILLIMETER));
        disciplines.add(new Discipline("Triple Jump", ResultType.CENTIMETER));
        disciplineRepository.saveAll(disciplines);
    }

    private void createClubs() {
        clubs.add(new Club("Tigers"));
        clubs.add(new Club("Lions"));
        clubs.add(new Club("Eagles"));
        clubs.add(new Club("Sharks"));
        clubs.add(new Club("Wolves"));
        clubs.add(new Club("Bears"));
        clubs.add(new Club("Hawks"));
        clubs.add(new Club("Panthers"));
        clubRepository.saveAll(clubs);
    }

    private void createAthletes() {
        athletes.add(new Athlete("Ali", "Haider", "Mohammad", LocalDate.of(1995, 5, 15), Gender.MALE, clubs.get(0)));
        athletes.add(new Athlete("Berfin", "Flora", "Turan", LocalDate.of(1998, 3, 22), Gender.FEMALE, clubs.get(1)));
        athletes.add(new Athlete("John", "Andrew", "Doe", LocalDate.of(1992, 8, 10), Gender.MALE, clubs.get(2)));
        athletes.add(new Athlete("Jane", "Alicia", "Smith", LocalDate.of(2000, 1, 5), Gender.FEMALE, clubs.get(3)));
        athletes.add(new Athlete("Michael", "James", "Johnson", LocalDate.of(1989, 11, 14), Gender.MALE, clubs.get(4)));
        athletes.add(new Athlete("Emily", "Rose", "Davis", LocalDate.of(1996, 7, 30), Gender.FEMALE, clubs.get(5)));
        athletes.add(new Athlete("Chris", "David", "Brown", LocalDate.of(1994, 4, 18), Gender.OTHER, clubs.get(6)));
        athletes.add(new Athlete("Sophia", "Marie", "Martinez", LocalDate.of(2001, 2, 20), Gender.FEMALE, clubs.get(7)));
        athletes.add(new Athlete("Daniel", "Lee", "Wilson", LocalDate.of(1993, 9, 25), Gender.MALE, clubs.get(0)));
        athletes.add(new Athlete("Laura", "Ann", "Taylor", LocalDate.of(1997, 6, 17), Gender.FEMALE, clubs.get(1)));
        athletes.add(new Athlete("David", "Allen", "Moore", LocalDate.of(1990, 10, 12), Gender.OTHER, clubs.get(2)));
        athletes.add(new Athlete("Amy", "Elizabeth", "Thomas", LocalDate.of(1999, 12, 5), Gender.FEMALE, clubs.get(3)));
        athleteRepository.saveAll(athletes);
    }
}
