package dk.almo.backend.models;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class AthleteTest {

    @Test
    void getFullNameWithMiddleName() {
        // Arrance
        Athlete student = new Athlete(
                "Ali",
                "Haider",
                "Mohammad",
                null,
                null
        );

        // Act
        String fullName = student.getFullName();

        // Assert
        assertEquals("Ali Haider Mohammad", fullName);
    }

    @Test
    void getFullNameWithoutMiddleName() {
        // Arrange
        Athlete student = new Athlete(
                "Ali",
                null,
                "Mohammad",
                null,
                null
        );

        // Act
        String fullName = student.getFullName();

        // Assert
        assertEquals("Ali Mohammad", fullName);
    }

}