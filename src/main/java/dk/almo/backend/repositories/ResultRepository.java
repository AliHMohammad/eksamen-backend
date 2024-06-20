package dk.almo.backend.repositories;

import dk.almo.backend.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findAllByAthleteId(Long athleteId);
}
