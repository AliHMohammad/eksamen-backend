package dk.almo.backend.config;

import dk.almo.backend.models.Athlete;
import dk.almo.backend.models.Gender;
import dk.almo.backend.repositories.AthleteRepository;
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

    public InitData(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Creating initial data:");
        createInitialData();
    }

    private void createInitialData() {
        //TODO: Create data
        createAthletes();
    }

    private void createAthletes() {
        List<Athlete> athletes = new ArrayList<>();

        athletes.add(new Athlete("ali", "haider", "mohammad", LocalDate.now(), Gender.MALE));
        athletes.add(new Athlete("berfin flORA tuRAN", LocalDate.now(), Gender.FEMALE));

        athleteRepository.saveAll(athletes);

    }

}



