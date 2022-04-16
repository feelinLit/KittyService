package com.itmo.kotiki.dto;

import com.itmo.kotiki.entity.Role;

import java.time.LocalDate;
import java.util.Objects;

public class PersonDto {

    private final Long id;
    private final String name;
    private final LocalDate dateOfBirth;
    private final String username;
    private final String password;
    private final Role role;

    public PersonDto(Long id, String name, String username, String password, Role role, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDto entity = (PersonDto) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.dateOfBirth, entity.dateOfBirth) &&
                Objects.equals(this.username, entity.username) &&
                Objects.equals(this.password, entity.password) &&
                Objects.equals(this.role, entity.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth, username, password, role);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "dateOfBirth = " + dateOfBirth + ", " +
                "username = " + username + ", " +
                "password = " + password + ", " +
                "role = " + role + ")";
    }

    public Long getId() {
        return id;
    }
}
