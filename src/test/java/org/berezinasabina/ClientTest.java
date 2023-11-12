package org.berezinasabina;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void testClientCreationAndGetters() {
        String firstName = "John";
        String lastName = "Doe";
        String gender = "M";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);

        Client client = new Client(firstName, lastName, gender, birthDate);

        assertEquals(firstName, client.getFirstName());
        assertEquals(lastName, client.getLastName());
        assertEquals(gender, client.getGender());
        assertEquals(birthDate, client.getBirthDate());
    }

    @Test
    void testClientEquality() {
        Client client1 = new Client("John", "Doe", "M", LocalDate.of(1990, 1, 1));
        Client client2 = new Client("John", "Doe", "M", LocalDate.of(1990, 1, 1));
        Client client3 = new Client("Jane", "Doe", "F", LocalDate.of(1990, 1, 1));

        assertEquals(client1, client2);
        assertNotEquals(client1, client3);
    }

    @Test
    void testClientHashCode() {
        Client client1 = new Client("John", "Doe", "M", LocalDate.of(1990, 1, 1));
        Client client2 = new Client("John", "Doe", "M", LocalDate.of(1990, 1, 1));

        assertEquals(client1.hashCode(), client2.hashCode());
    }

    @Test
    void testClientToString() {
        Client client = new Client("John", "Doe", "M", LocalDate.of(1990, 1, 1));

        String expectedToString = "Client{firstName='John', lastName='Doe', gender='M', birthDate=1990-01-01}";
        assertEquals(expectedToString, client.toString());
    }
}
