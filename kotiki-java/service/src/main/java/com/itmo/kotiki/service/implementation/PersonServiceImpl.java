package com.itmo.kotiki.service.implementation;

import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.entity.Person;
import com.itmo.kotiki.repository.KittyRepository;
import com.itmo.kotiki.repository.PersonRepository;
import com.itmo.kotiki.service.PersonService;
import com.itmo.kotiki.tool.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final KittyRepository kittyRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, KittyRepository kittyRepository) {
        this.personRepository = personRepository;
        this.kittyRepository = kittyRepository;
    }

    @Override
    public Person save(Person entity) {
        if (entity.getDateOfBirth() == null)
            entity.setDateOfBirth(LocalDate.now());
        return personRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public Person saveOrUpdate(Long id, Person entity) {
        Person person = personRepository.findById(id)
                .map(personFound -> {
                    personFound.setName(entity.getName());
                    if (entity.getDateOfBirth() != null) personFound.setDateOfBirth(entity.getDateOfBirth());
                    return personFound;
                })
                .orElse(entity);
        return save(person);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username);
        if (person == null) {
            throw new UsernameNotFoundException(username);
        }
        return person;
    }

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new DomainException("Person wasn't found"));
    }

    @Override
    public Person findByUsername(String username) {
        var person = personRepository.findByUsername(username);
        if (person == null) {
            throw new EntityNotFoundException(username);
        }
        return person;
    }

    @Override
    public void delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new DomainException("Person wasn't found"));
        personRepository.delete(person);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> findAll(String name) {
        return personRepository.findAllByName(name);
    }

    @Override
    public void deleteAll() {
        personRepository.deleteAll();
    }

    @Override
    public void addKitty(Long personId, Long kittyId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new DomainException("Person wasn't found"));
        Kitty kitty = kittyRepository.findById(kittyId)
                .orElseThrow(() -> new DomainException("Kitty wasn't found"));
        person.addKitty(kitty);
        personRepository.saveAndFlush(person);
        kittyRepository.saveAndFlush(kitty);
    }
}
