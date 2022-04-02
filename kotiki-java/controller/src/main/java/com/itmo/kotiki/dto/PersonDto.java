package com.itmo.kotiki.dto;

import java.time.LocalDate;

public class PersonDto {

    private final Long id;
    private final String name;
    private final LocalDate dateOfBirth;

    public PersonDto(Long id, String name, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Long getId() {
        return id;
    }
}
