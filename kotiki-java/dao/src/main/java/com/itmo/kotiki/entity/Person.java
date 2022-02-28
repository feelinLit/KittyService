package com.itmo.kotiki.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person extends BaseEntity {
    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "person", orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<Kitty> kitties = new ArrayList<>();

    public Person(String name, LocalDate dateOfBirth, Kitty kitty) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        if (kitty != null)
            addKitty(kitty);
    }

    protected Person() { }

    public List<Kitty> getKitties() {
        return kitties;
    }

    public void addKitty(Kitty kitty) {
        if (kitty.getPerson() != null && kitty.getPerson() != this)
            throw new RuntimeException(); // TODO: exceptions
        this.kitties.add(kitty);
        kitty.setPerson(this);
    }

//    public void setKitties(List<Kitty> kitties) {
//        this.kitties = kitties;
//    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
