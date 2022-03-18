package com.itmo.kotiki.entity;

import com.itmo.kotiki.tool.DaoException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "kitty")
public class Kitty extends BaseEntity {
    @Column(name = "breed", length = 60)
    private String breed;

    @Column(name = "name", length = 60)
    private String name;

    @Enumerated
    @Column(name = "color", columnDefinition = "TEXT")
    private Color color;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "person_ID")
    private Person person;

    @OneToMany(mappedBy = "kitty", orphanRemoval = true)
    private final List<KittyFriendship> kittyFriendships = new ArrayList<>();

    protected Kitty() {
    }

    public Kitty(String breed, String name, Color color, LocalDate dateOfBirth, Person person) {
        this.breed = breed;
        this.name = name;
        this.color = color;
        this.dateOfBirth = dateOfBirth;
        this.person = person;
    }

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

    public List<KittyFriendship> getKittyFriendships() {
        return Collections.unmodifiableList(kittyFriendships);
    }

    public KittyFriendship addFriend(Kitty kitty) {
        if (kitty == null)
            throw new DaoException("Kitty-friend can't be null");
        if (kitty == this)
            throw new DaoException("Kitty can't be friend of itself, it's schizo");
        if (kittyFriendships.stream().anyMatch(kittyFriendship -> kittyFriendship.getKittysFriend().getId().equals(getId())))
            throw new DaoException("Kitties are already friends :3");
        var kittyFriendship = new KittyFriendship(this, kitty);
        kittyFriendships.add(kittyFriendship);
        return kittyFriendship;
    }
}
