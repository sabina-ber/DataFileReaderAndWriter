package org.berezinasabina;

import java.time.LocalDate;

public class Client {
    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate birthDate;

    public Client() {
    }

    public Client(String firstName, String lastName, String gender, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    // toString() метод для удобного отображения информации о клиенте
    @Override

    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Client)) {
            return false;
        }

        Client client = (Client) o;

        if (!firstName.equals(client.firstName)) {
            return false;
        }
        if (!lastName.equals(client.lastName)) {
            return false;
        }
        if (!gender.equals(client.gender)) {
            return false;
        }
        return birthDate.equals(client.birthDate);
    }

    @Override

    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + gender.hashCode();
        result = 31 * result + birthDate.hashCode();
        return result;
    }
}
