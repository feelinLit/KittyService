package com.itmo.kotiki.entity;

import com.itmo.kotiki.tool.DaoException;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person extends BaseEntity {

    @OneToMany(mappedBy = "person", orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<Kitty> kitties = new ArrayList<>();

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User user;

    protected Person() {
    }

    public Person(String name, LocalDate dateOfBirth, Kitty kitty, User user) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        if (kitty != null)
            addKitty(kitty);
        this.user = user;
    }

    public Person(Long id, String name, LocalDate dateOfBirth, User user) {
        setId(id);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Kitty> getKitties() {
        return kitties;
    }

    public void addKitty(Kitty kitty) {
        if (kitty.getPerson() != null && kitty.getPerson() != this)
            throw new DaoException("Kitty already has an owner: " + kitty.getPerson().getName());
        this.kitties.add(kitty);
        kitty.setPerson(this);
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
