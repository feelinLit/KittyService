package com.clients;

import com.tools.BanksException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DefaultClient implements Client {
    public final TrustworthinessRaiseEvent TrustworthinessRaiseEvent =
            new TrustworthinessRaiseEvent();
    private final List<String> notifications = new ArrayList<>();
    private String name;
    private String surname;
    private String address;
    private String passport;
    private int phoneNumber;
    private boolean isTrustworthy;

    public DefaultClient(
            String name, String surname, String address, String passport, int phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passport;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws BanksException {
        if (name.isEmpty()) throw new BanksException("Client's name can't be empty");
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws BanksException {
        if (surname.isEmpty()) throw new BanksException("Client's surname can't be empty");
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        changeTrustworthiness();
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
        changeTrustworthiness();
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) throws BanksException {
        if (phoneNumber == 0) throw new BanksException("Client's phone number can't be 0");
        this.phoneNumber = phoneNumber;
    }

    public List<String> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }

    public void changeTrustworthiness() {
        if (passport == null && address == null) return;
        isTrustworthy = true;
        TrustworthinessRaiseEvent.invoke();
    }

    @Override
    public void onConditionChanged(String message) {
        notifications.add(message);
    }

    public boolean isTrustworthy() {
        return isTrustworthy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultClient that = (DefaultClient) o;
        return phoneNumber == that.phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }
}
