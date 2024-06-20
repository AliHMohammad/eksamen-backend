package dk.almo.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    @JsonIgnore
    @ManyToMany(mappedBy = "disciplines")
    private Set<Athlete> athletes = new HashSet<>();


    public Discipline(String name, ResultType resultType) {
        this.name = name;
        this.resultType = resultType;
    }

    public String getResultType() {
        return resultType.toString().substring(0, 1).toUpperCase() + resultType.toString().substring(1).toLowerCase();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return Objects.equals(name, that.name) && resultType == that.resultType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, resultType);
    }
}
