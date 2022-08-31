package com.itmo.kotiki.dto;

import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;

import java.io.Serializable;
import java.time.LocalDate;

public class KittyDto implements Serializable {

    private final Long id;
    private final String name;
    private final String breed;
    private final Color color;
    private final LocalDate dateOfBirth;
    private final PersonDto person;

    public KittyDto(Long id, String name, String breed, Color color, LocalDate dateOfBirth, PersonDto person) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.dateOfBirth = dateOfBirth;
        this.person = person;
    }

    public KittyDto(Kitty kitty) {
        this.id = kitty.getId();
        this.name = kitty.getName();
        this.breed = kitty.getBreed();
        this.color = kitty.getColor();
        this.dateOfBirth = kitty.getDateOfBirth();
        this.person = PersonDto.convertToDto(kitty.getPerson());
    }

    public Kitty convertToEntity() {
        return new Kitty(this.getBreed(), this.getName(), this.getColor(), this.getDateOfBirth(), this.person.convertToEntity());
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

    public PersonDto getPerson() {
        return person;
    }
}
