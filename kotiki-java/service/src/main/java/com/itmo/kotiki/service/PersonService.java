package com.itmo.kotiki.service;

import com.itmo.kotiki.entity.Person;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PersonService extends Service<Person>, UserDetailsService {

    Person save(Person entity);

    Person saveOrUpdate(Long id, Person entity);

    void addKitty(Long personId, Long kittyId);

    List<Person> findAll(String name);

    Person findByUsername(String username);
}
