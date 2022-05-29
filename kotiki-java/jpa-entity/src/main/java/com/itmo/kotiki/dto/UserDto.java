package com.itmo.kotiki.dto;

import com.itmo.kotiki.entity.Role;
import com.itmo.kotiki.entity.User;

import java.io.Serializable;
import java.util.Objects;

public class UserDto implements Serializable {
    private final Long id;
    private final String username;
    private final String password;
    private final Role role;

    public UserDto(Long id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public User convertToEntity() {
        return new User(this.getUsername(), this.getPassword(), this.getRole());
    }

    public Long getId() {
        return id;
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
        UserDto entity = (UserDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.username, entity.username) &&
                Objects.equals(this.password, entity.password) &&
                Objects.equals(this.role, entity.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "username = " + username + ", " +
                "password = " + password + ", " +
                "role = " + role + ")";
    }
}
