package com.itmo.banksjava.client;

import com.itmo.banksjava.tool.BanksException;

public class ClientBuilder {
    private String name;
    private String surname;
    private String address;
    private String passport;
    private int phoneNumber;

    public ClientBuilder() {
        Reset();
    }

    public ClientBuilder setName(String name) throws BanksException {
        if (name.isEmpty()) throw new BanksException("Name can't be empty");

        this.name = name;
        return this;
    }

    public ClientBuilder setSurname(String surname) throws BanksException {
        if (surname.isEmpty()) throw new BanksException("Name can't be empty");

        this.surname = surname;
        return this;
    }

    public ClientBuilder setAddress(String address) {
        if (address.isEmpty()) return this;

        this.address = address;
        return this;
    }

    public ClientBuilder SetPassport(String passport) {
        if (passport.isEmpty()) return this;

        this.passport = passport;
        return this;
    }

    public ClientBuilder SetPhoneNumber(int number) throws BanksException {
        if (number == 0) throw new BanksException("Client's phone number can't be 0");

        this.phoneNumber = number;
        return this;
    }

    public ClientBuilder Reset() {
        this.name = null;
        this.surname = null;
        this.address = null;
        this.passport = null;
        this.phoneNumber = 0;
        return this;
    }

    public DefaultClient RetrieveResult() throws BanksException {
        if (this.name.isEmpty()) throw new BanksException("Client's name can't be empty");
        if (this.surname.isEmpty()) throw new BanksException("Client's surname can't be empty");
        if (this.phoneNumber == 0) throw new BanksException("Client's phone number can't be empty");
        var client =
                new DefaultClient(this.name, this.surname, this.address, this.passport, this.phoneNumber);
        Reset();
        return client;
    }
}
