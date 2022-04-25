package com.itmo.kotiki.entity;

import com.itmo.kotiki.tool.DaoException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "person")
public class Person extends BaseEntity implements UserDetails {

    @OneToMany(mappedBy = "person", orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<Kitty> kitties = new ArrayList<>();

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Enumerated
    @Column(name = "role")
    private Role role;

    protected Person() {
    }

    public Person(String name, String username, String password, Role role, LocalDate dateOfBirth, Kitty kitty) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        if (kitty != null)
            addKitty(kitty);
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
