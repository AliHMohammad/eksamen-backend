package dk.almo.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    private Club club;

    @ManyToMany
    private Set<Discipline> disciplines = new HashSet<>();

    public Athlete(String firstName, String middleName, String lastName, LocalDate dateOfBirth, Gender gender) {
        this(firstName, middleName, lastName, dateOfBirth, gender, null);
    }

    public Athlete(String firstName, String middleName, String lastName, LocalDate dateOfBirth, Gender gender, Club club) {
        this.firstName = capitalize(firstName);
        this.middleName = capitalize(middleName);
        this.lastName = capitalize(lastName);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.club = club;
    }

    public Athlete(String fullName, LocalDate dateOfBirth, Gender gender) {
        this(fullName, dateOfBirth, gender, null);
    }

    public Athlete(String fullName, LocalDate dateOfBirth, Gender gender, Club club) {
        setFullName(capitalize(fullName));
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.club = club;
    }

    public int getAge() {
        return dateOfBirth.until(LocalDate.now()).getYears();
    }

    public String getFullName() {
        return getFirstName() + " " + (getMiddleName() != null ? getMiddleName() + " " : "") + getLastName();
    }

    public void setFullName(String fullName) {
        // Null
        if (fullName == null) return;

        int firstSpace = fullName.indexOf(" ");
        int lastSpace = fullName.lastIndexOf(" ");

        // First name only or Empty string
        if (firstSpace == -1) {
            setFirstName(fullName);
            setMiddleName(null);
            setLastName(null);
            return;
        }

        setFirstName(fullName.substring(0, firstSpace));
        setMiddleName((firstSpace != lastSpace ? fullName.substring(firstSpace + 1, lastSpace) : null));
        setLastName(fullName.substring(lastSpace + 1));
    }

    public String capitalize(String name) {
        if (name == null) return null;
        // Empty or one letter
        if (name.length() < 2) return name.toUpperCase();

        // Recursion, Hvis mellemnavn består af flere navne
        if (name.contains(" ")){
            int space = name.indexOf(" ");
            return capitalize(name.substring(0, space)) + " " + capitalize(name.substring(space + 1));
        }

        return name.substring(0, 1).toUpperCase()+name.substring(1).toLowerCase();
    }
}