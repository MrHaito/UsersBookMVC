package ru.klopskiy.usersbook.model;

import javax.validation.constraints.*;
import java.util.List;

public class Person {
    private int person_id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно быть длиной от 2 до 30 символов")
    private String name;

    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    private int yearOfBirth;

    public Person() {
    }

    public Person(String name, int year, List<Book> books) {
        this.name = name;
        this.yearOfBirth = year;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
