package com.itmo.kotiki.service;

import com.itmo.kotiki.entity.Person;

import java.util.List;

public interface PersonService extends Service<Person> {
    void addKitty(Long personId, Long kittyId);

    List<Person> findAll(String name);
}
