package com.itmo.kotiki.personservice.repository;

import com.itmo.kotiki.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findAllByName(String name);

    Person findPersonByUser_Username(String username);
}