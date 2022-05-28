package com.itmo.kotiki.service;

import com.itmo.kotiki.entity.Person;

import java.util.List;

public interface PersonService extends Service<Person> {

    Person save(Person entity);

    Person saveOrUpdate(Long id, Person entity);

    void addKitty(Long personId, Long kittyId);

    List<Person> findAll(String name);

    Person findByUsername(String username);
}
