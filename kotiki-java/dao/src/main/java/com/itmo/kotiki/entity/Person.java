package com.itmo.kotiki.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person extends BaseEntity {
    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "person", orphanRemoval = true)
    private List<Kitty> kitties = new ArrayList<>();

    public List<Kitty> getKitties() {
        return kitties;
    }

    public void setKitties(List<Kitty> kitties) {
        this.kitties = kitties;
    }

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
