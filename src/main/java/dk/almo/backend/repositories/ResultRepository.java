package dk.almo.backend.repositories;

import dk.almo.backend.models.Discipline;
import dk.almo.backend.models.Gender;
import dk.almo.backend.models.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findAllByAthleteId(Long athleteId);

    Page<Result> findAllByAthleteGenderAndDisciplineId(Gender athlete_gender, Long discipline_id, Pageable pageable);

    Page<Result> findAllByDisciplineId(Long discipline_id, Pageable pageable);

    void deleteAllByAthleteId(Long athlete_id);
}
