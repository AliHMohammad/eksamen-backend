package dk.almo.backend.config;

import dk.almo.backend.models.Athlete;
import dk.almo.backend.models.Club;
import dk.almo.backend.models.Gender;
import dk.almo.backend.repositories.AthleteRepository;
import dk.almo.backend.repositories.ClubRepository;
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

    public InitData(AthleteRepository athleteRepository, ClubRepository clubRepository) {
        this.athleteRepository = athleteRepository;
        this.clubRepository = clubRepository;
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
    }

    private void createClubs() {
        List<Club> clubs = new ArrayList<>();

        clubs.add(new Club("Tigers"));

        clubRepository.saveAll(clubs);
    }

    private void createAthletes() {
        List<Athlete> athletes = new ArrayList<>();

        athletes.add(new Athlete("ali", "haider", "mohammad", LocalDate.now(), Gender.MALE));
        athletes.add(new Athlete("berfin flORA tuRAN", LocalDate.now(), Gender.FEMALE));

        athleteRepository.saveAll(athletes);

    }

}



