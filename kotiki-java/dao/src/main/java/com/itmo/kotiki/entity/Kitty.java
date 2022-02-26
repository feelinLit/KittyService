package com.itmo.kotiki.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Kitty")
public class Kitty extends BaseEntity {
    @Column(name = "breed", length = 60)
    private String breed;

    @Column(name = "name")
    private String name;

    @Enumerated
    @Column(name = "color")
    private Color color;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "person_ID")
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
