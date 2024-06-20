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

/*
    @Test
    void setFullNameWithMiddleName() {
        // Arrange
        Athlete student = new Athlete(
                "first",
                "middle",
                "last",
                null,
                0,
                null,
                null
        );

        String fullName = "Harry James Potter";

        // Act
        student.setFullName(fullName);

        // Assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James", student.getMiddleName());
        assertEquals("Potter", student.getLastName());

    }

    @Test
    void setFullNameWithoutMiddleName() {
        // Arrange
        Athlete student = new Athlete(
                "first",
                "middle",
                "last",
                null,
                0,
                null,
                null
        );

        String fullName = "Harry Potter";

        // Act
        student.setFullName(fullName);

        // Assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("Potter", student.getLastName());

        assertNull(student.getMiddleName());
    }


    @Test
    void setFullNameWithEmptyString() {
        // Arrange
        Athlete student = new Athlete(
                "first",
                "middle",
                "last",
                null,
                0,
                null,
                null
        );

        String fullName = "";

        // Act
        student.setFullName(fullName);

        // Assert
        assertEquals("", student.getFirstName());

        assertNull(student.getLastName());
        assertNull(student.getMiddleName());
    }

    @Test
    void setFullNameWithNull() {
        // Arrange
        Athlete student = new Athlete(
                "first",
                "middle",
                "last",
                null,
                0,
                null,
                null
        );

        // Act
        student.setFullName(null);

        // Assert
        assertEquals("first", student.getFirstName());
        assertEquals("middle", student.getMiddleName());
        assertEquals("last", student.getLastName());
    }

    @Test
    void setFullNameWithMultipleMiddleNames() {
        // Arrange
        Athlete student = new Athlete(
                "first",
                "middle",
                "last",
                null,
                0,
                null,
                null
        );

        // Act
        student.setFullName("Ali Abed Al Amier");

        // Assert
        assertEquals("Ali", student.getFirstName());
        assertEquals("Abed Al", student.getMiddleName());
        assertEquals("Amier", student.getLastName());
    }

    @Test
    void setFullNameWithOnlyFirstName() {
        // Arrange
        Athlete student = new Athlete(
                "first",
                "middle",
                "last",
                null,
                0,
                null,
                null
        );

        // Act
        student.setFullName("Ali");

        // Assert
        assertEquals("Ali", student.getFirstName());
        assertNull(student.getMiddleName());
        assertNull(student.getLastName());
    }

    @Test
    void capitalizeIndividualNames() {
        // Arrange
        Athlete student = new Athlete(
                "first",
                "middle",
                "last",
                null,
                0,
                null,
                null
        );

        // Act
        student.setFirstName("harry");
        student.setMiddleName("james");
        student.setLastName("potter");

        // Assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James", student.getMiddleName());
        assertEquals("Potter", student.getLastName());
    }

    @Test
    void capitalizeIndividualNamesWithCrazyCapitalization() {
        // Arrange
        Athlete student = new Athlete(
                "first",
                "middle",
                "last",
                null,
                0,
                null,
                null
        );

        // Act
        student.setFirstName("haRrY");
        student.setMiddleName("jAMeS");
        student.setLastName("PoTteR");

        // Assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James", student.getMiddleName());
        assertEquals("Potter", student.getLastName());
    }

    @Test
    void capitalizeIndividualNamesWithOnlyOneLetter() {
        // Arrange
        Athlete student = new Athlete(
                "first",
                "middle",
                "last",
                null,
                0,
                null,
                null
        );

        // Act
        student.setFirstName("haRrY");
        student.setMiddleName("j");
        student.setLastName("P");

        // Assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("J", student.getMiddleName());
        assertEquals("P", student.getLastName());
    }


    @Test
    void makeFifthYearAthletePrefect() {
        // Arrange
        Athlete student = new Athlete(
                "first",
                "middle",
                "last",
                null,
                5,
                false,
                null
        );

        // Act
        student.setPrefect(true);

        // Assert
        assertTrue(student.getPrefect());
    }

    @Test
    void makeFourthYearAthletePrefect() {
        // Arrange
        Athlete student = new Athlete(
                "first",
                "middle",
                "last",
                null,
                4,
                false,
                null
        );

        // Act
        student.setPrefect(true);

        // Assert
        assertFalse(student.getPrefect());
    }*/

}