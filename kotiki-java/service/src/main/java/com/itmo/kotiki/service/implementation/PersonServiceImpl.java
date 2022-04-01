package com.itmo.kotiki.service.implementation;

import com.itmo.kotiki.entity.Person;
import com.itmo.kotiki.repository.PersonRepository;
import com.itmo.kotiki.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person save(Person entity) {
        if (entity.getDateOfBirth() == null)
            entity.setDateOfBirth(LocalDate.now());
        return personRepository.saveAndFlush(entity);
    }

    @Override
    public Person saveOrUpdate(Long id, Person entity) {
        Person person = personRepository.findById(id)
                .map(personFound -> {
                    personFound.setName(entity.getName());
                    if (entity.getDateOfBirth() != null) personFound.setDateOfBirth(entity.getDateOfBirth());
                    return personFound;})
                .orElse(entity);
        return save(person);
    }

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person wasn't found"));
    }

    @Override
    public void delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person wasn't found"));
        personRepository.delete(person);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public void deleteAll() {
        personRepository.deleteAll();
    }
}
