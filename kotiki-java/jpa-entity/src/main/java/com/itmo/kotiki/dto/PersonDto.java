package com.itmo.kotiki.dto;

import com.itmo.kotiki.entity.Person;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class PersonDto implements Serializable {
    private final Long id;
    private final String name;
    private final LocalDate dateOfBirth;
    private final UserDto user;

    public PersonDto(Long id, String name, LocalDate dateOfBirth, UserDto user) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.user = user;
    }

    public static PersonDto convertToDto(Person person) {
        return new PersonDto(person.getId(), person.getName(), person.getDateOfBirth(), UserDto.convertToDto(person.getUser()));
    }

    public Person convertToEntity() {
        return new Person(this.id, this.getName(), this.getDateOfBirth(), this.getUser().convertToEntity());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public UserDto getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDto entity = (PersonDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.dateOfBirth, entity.dateOfBirth) &&
                Objects.equals(this.user, entity.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfBirth, user);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "dateOfBirth = " + dateOfBirth + ", " +
                "user = " + user + ")";
    }
}
