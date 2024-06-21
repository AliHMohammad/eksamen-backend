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
        results.add(new Result(LocalDate.now(), 1056340L, disciplines.get(0), athletes.get(0)));
        results.add(new Result(LocalDate.now(), 103240L, disciplines.get(0), athletes.get(0)));
        results.add(new Result(LocalDate.now(), 1022140L, disciplines.get(0), athletes.get(0)));
        results.add(new Result(LocalDate.now(), 1034220L, disciplines.get(0), athletes.get(0)));
        results.add(new Result(LocalDate.now(), 2053454L, disciplines.get(0), athletes.get(2)));
        results.add(new Result(LocalDate.now(), 30423420L, disciplines.get(0), athletes.get(2)));
        results.add(new Result(LocalDate.now(), 423442323, disciplines.get(0), athletes.get(2)));
        results.add(new Result(LocalDate.now(), 43243242L, disciplines.get(0), athletes.get(2)));
        results.add(new Result(LocalDate.now(), 44332342L, disciplines.get(0), athletes.get(3)));
        results.add(new Result(LocalDate.now(), 34323232, disciplines.get(0), athletes.get(3)));
        results.add(new Result(LocalDate.now(), 234L, disciplines.get(1), athletes.get(0)));
        results.add(new Result(LocalDate.now(), 343L, disciplines.get(1), athletes.get(0)));
        results.add(new Result(LocalDate.now(), 322L, disciplines.get(1), athletes.get(0)));
        results.add(new Result(LocalDate.now(), 123L, disciplines.get(1), athletes.get(1)));
        results.add(new Result(LocalDate.now(), 132L, disciplines.get(1), athletes.get(1)));
        results.add(new Result(LocalDate.now(), 232L, disciplines.get(1), athletes.get(3)));
        results.add(new Result(LocalDate.now(), 126L, disciplines.get(1), athletes.get(3)));
        results.add(new Result(LocalDate.now(), 150L, disciplines.get(1), athletes.get(9)));
        results.add(new Result(LocalDate.now(), 170L, disciplines.get(1), athletes.get(9)));
        results.add(new Result(LocalDate.now(), 230L, disciplines.get(1), athletes.get(10)));
        results.add(new Result(LocalDate.now(), 126L, disciplines.get(1), athletes.get(10)));
        results.add(new Result(LocalDate.now(), 220L, disciplines.get(1), athletes.get(10)));
        results.add(new Result(LocalDate.now(), 320L, disciplines.get(1), athletes.get(11)));
        results.add(new Result(LocalDate.now(), 344L, disciplines.get(1), athletes.get(11)));
        results.add(new Result(LocalDate.now(), 354L, disciplines.get(1), athletes.get(11)));
        results.add(new Result(LocalDate.now(), 32L, disciplines.get(2), athletes.get(1)));
        results.add(new Result(LocalDate.now(), 12L, disciplines.get(2), athletes.get(3)));
        results.add(new Result(LocalDate.now(), 20L, disciplines.get(2), athletes.get(4)));
        results.add(new Result(LocalDate.now(), 32L, disciplines.get(2), athletes.get(5)));
        results.add(new Result(LocalDate.now(), 22L, disciplines.get(2), athletes.get(6)));
        results.add(new Result(LocalDate.now(), 32434, disciplines.get(3), athletes.get(7)));
        results.add(new Result(LocalDate.now(), 32443, disciplines.get(3), athletes.get(7)));
        results.add(new Result(LocalDate.now(), 23433, disciplines.get(3), athletes.get(8)));
        results.add(new Result(LocalDate.now(), 23322, disciplines.get(3), athletes.get(5)));
        results.add(new Result(LocalDate.now(), 23421, disciplines.get(3), athletes.get(5)));
        results.add(new Result(LocalDate.now(), 23767, disciplines.get(3), athletes.get(6)));
        results.add(new Result(LocalDate.now(), 23422, disciplines.get(3), athletes.get(6)));
        results.add(new Result(LocalDate.now(), 23434, disciplines.get(3), athletes.get(6)));
        resultRepository.saveAll(results);
    }

    private void createAthletesDisciplines() {
        athletes.get(0).getDisciplines().add(disciplines.get(0));
        athletes.get(0).getDisciplines().add(disciplines.get(1));
        athletes.get(1).getDisciplines().add(disciplines.get(1));
        athletes.get(1).getDisciplines().add(disciplines.get(2));
        athletes.get(1).getDisciplines().add(disciplines.get(3));
        athletes.get(2).getDisciplines().add(disciplines.get(0));
        athletes.get(2).getDisciplines().add(disciplines.get(2));
        athletes.get(2).getDisciplines().add(disciplines.get(3));
        athletes.get(3).getDisciplines().add(disciplines.get(0));
        athletes.get(3).getDisciplines().add(disciplines.get(1));
        athletes.get(3).getDisciplines().add(disciplines.get(2));
        athletes.get(4).getDisciplines().add(disciplines.get(2));
        athletes.get(4).getDisciplines().add(disciplines.get(3));
        athletes.get(4).getDisciplines().add(disciplines.get(2));
        athletes.get(5).getDisciplines().add(disciplines.get(2));
        athletes.get(5).getDisciplines().add(disciplines.get(3));
        athletes.get(5).getDisciplines().add(disciplines.get(3));
        athletes.get(6).getDisciplines().add(disciplines.get(2));
        athletes.get(6).getDisciplines().add(disciplines.get(2));
        athletes.get(7).getDisciplines().add(disciplines.get(3));
        athletes.get(8).getDisciplines().add(disciplines.get(3));
        athletes.get(9).getDisciplines().add(disciplines.get(1));
        athletes.get(10).getDisciplines().add(disciplines.get(1));
        athletes.get(11).getDisciplines().add(disciplines.get(1));

        athleteRepository.saveAll(athletes);
    }

    private void createDisciplines() {
        disciplines.add(new Discipline("Rowing 300m", ResultType.MILLISECONDS));
        disciplines.add(new Discipline("Long Jump", ResultType.CENTIMETER));
        disciplines.add(new Discipline("Dart Throwing", ResultType.POINTS));
        disciplines.add(new Discipline("100m Sprint", ResultType.MILLISECONDS));

        disciplineRepository.saveAll(disciplines);
    }

    private void createClubs() {
        clubs.add(new Club("Tigers"));
        clubs.add(new Club("Lions"));
        clubs.add(new Club("Eagles"));
        clubRepository.saveAll(clubs);
    }

    private void createAthletes() {
        athletes.add(new Athlete("Ali", "Haider", "Mohammad", LocalDate.of(1995, 5, 15), Gender.MALE, clubs.get(0)));
        athletes.add(new Athlete("Berfin", "Flora", "Turan", LocalDate.of(1998, 3, 22), Gender.FEMALE, clubs.get(1)));
        athletes.add(new Athlete("John", "Andrew", "Doe", LocalDate.of(1992, 8, 10), Gender.MALE, clubs.get(2)));
        athletes.add(new Athlete("Sama", "Alicia", "Smith", LocalDate.of(2000, 1, 5), Gender.FEMALE, clubs.get(0)));
        athletes.add(new Athlete("Michael", "James", "Johnson", LocalDate.of(1989, 11, 14), Gender.MALE, clubs.get(1)));
        athletes.add(new Athlete("Emily", "Rose", "Davis", LocalDate.of(1996, 7, 30), Gender.FEMALE, clubs.get(2)));
        athletes.add(new Athlete("Chris", "David", "Brown", LocalDate.of(1994, 4, 18), Gender.OTHER, clubs.get(1)));
        athletes.add(new Athlete("Sophia", "Marie", "Martinez", LocalDate.of(2001, 2, 20), Gender.FEMALE, clubs.get(1)));
        athletes.add(new Athlete("Daniel", "Lee", "Wilson", LocalDate.of(1993, 9, 25), Gender.MALE, clubs.get(0)));
        athletes.add(new Athlete("Laura", "Ann", "Taylor", LocalDate.of(1997, 6, 17), Gender.FEMALE, clubs.get(1)));
        athletes.add(new Athlete("David", "Allen", "Moore", LocalDate.of(1990, 10, 12), Gender.OTHER, clubs.get(0)));
        athletes.add(new Athlete("Amy", "Elizabeth", "Thomas", LocalDate.of(1999, 12, 5), Gender.FEMALE, clubs.get(0)));
        athleteRepository.saveAll(athletes);
    }
}
