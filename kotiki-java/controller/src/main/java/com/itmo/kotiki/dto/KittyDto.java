package com.itmo.kotiki.dto;

import com.itmo.kotiki.entity.Color;

import java.time.LocalDate;

public class KittyDto {

    private final Long id;
    private final String name;
    private final String breed;
    private final Color color;
    private final LocalDate dateOfBirth;

    public KittyDto(Long id, String name, String breed, Color color, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public Color getColor() {
        return color;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
