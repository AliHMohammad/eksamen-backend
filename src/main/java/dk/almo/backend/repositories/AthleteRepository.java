package dk.almo.backend.repositories;

import dk.almo.backend.models.Athlete;
import dk.almo.backend.models.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    Page<Athlete> findAllByDisciplinesId(Long disciplineId, Pageable pageable);

    Page<Athlete> findAllByClubIdAndDisciplinesId(Long club_id, Long disciplines_id, Pageable pageable);

    Page<Athlete> findAllByGenderAndDisciplinesId(Gender gender, Long disciplines_id, Pageable pageable);

    Page<Athlete> findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseAndDisciplinesId(String firstName, String lastName, Long disciplines_id,
                                                                                                   Pageable pageable);

    Page<Athlete> findAllByGenderAndClubIdAndDisciplinesId(Gender gender, Long club_id, Long disciplines_id, Pageable pageable);

    Page<Athlete> findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseAndClubIdAndDisciplinesId(String firstName, String lastName, Long club_id,
                                                                                                            Long disciplines_id, Pageable pageable);

    Page<Athlete> findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseAndGenderAndDisciplinesId(String firstName, String lastName, Gender gender,
                                                                                                            Long disciplines_id, Pageable pageable);

    Page<Athlete> findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseAndGenderAndClubIdAndDisciplinesId(
            String firstName,
            String lastName,
            Gender gender,
            Long club_id,
            Long disciplines_id,
            Pageable pageable
    );
}
