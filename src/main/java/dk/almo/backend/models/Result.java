package dk.almo.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(name = "result_value")
    private long value;

    @ManyToOne
    private Discipline discipline;

    @ManyToOne
    private Athlete athlete;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Result(LocalDate date, long value) {
        this.date = date;
        this.value = value;
    }

    public Result(LocalDate date, long value, Discipline discipline, Athlete athlete) {
        this.date = date;
        this.value = value;
        this.discipline = discipline;
        this.athlete = athlete;
    }
}
