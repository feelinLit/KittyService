package com.clients;

public class BaseClient implements Client {
    event EventHandler RaiseAccountTrustworthiness;

    private String name;
    private String surname;
    private String address;
    private String passport;
    private int phoneNumber;
    private boolean trustworthy;

    public TrustworthinessRaiseEvent TrustworthinessRaiseEvent = new TrustworthinessRaiseEvent();

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPassport() { return passport; }

    public void setPassport(String passport) { this.passport = passport; }

    public int getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(int phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public void notify(Object sender, String message) {

    }

    @Override
    public void onConditionChanged(String message) {

    }

    public boolean isTrustworthy() { return trustworthy; }
}
