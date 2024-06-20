package dk.almo.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private long value;

    @ManyToOne
    private Discipline discipline;

    @ManyToOne
    private Athlete athlete;

    public Result(LocalDate date, long value, Discipline discipline, Athlete athlete) {
        this.date = date;
        this.value = value;
        this.discipline = discipline;
        this.athlete = athlete;
    }
}
