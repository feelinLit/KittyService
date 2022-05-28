package com.itmo.kotiki.personservice.service;

import com.itmo.kotiki.dto.PersonDto;
import com.itmo.kotiki.entity.Person;
import com.itmo.kotiki.personservice.repository.PersonRepository;
import com.itmo.kotiki.personservice.tool.DomainException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @RabbitListener(queues = "person.rpc.requests.save")
    public PersonDto save(PersonDto entity) {
        var person = entity.convertToEntity();
        if (person.getDateOfBirth() == null)
            person.setDateOfBirth(LocalDate.now());
        return PersonDto.convertToDto(personRepository.saveAndFlush(person));
    }

    @Override
    @RabbitListener(queues = "person.rpc.requests.saveOrUpdate")
    @Transactional
    public PersonDto saveOrUpdate(PersonDto entity) {
        Person person = personRepository.findById(entity.getId())
                .map(personFound -> {
                    personFound.setName(entity.getName());
                    if (entity.getDateOfBirth() != null) personFound.setDateOfBirth(entity.getDateOfBirth());
                    return personFound;
                })
                .orElse(entity.convertToEntity());
        return save(PersonDto.convertToDto(person));
    }

    @Override
    @RabbitListener(queues = "person.rpc.requests.findById")
    public PersonDto findById(Long id) {
        return PersonDto.convertToDto(
                personRepository.findById(id)
                        .orElseThrow(() -> new DomainException("Person wasn't found")));

    }

    @Override
    @RabbitListener(queues = "person.rpc.requests.findByUsername")
    public PersonDto findByUsername(String username) {
        var person = personRepository.findPersonByUser_Username(username);
        if (person == null) {
            throw new EntityNotFoundException(username);
        }
        return PersonDto.convertToDto(person);
    }

    @Override
    @RabbitListener(queues = "person.rpc.requests.delete")
    public void delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new DomainException("Person wasn't found"));
        personRepository.delete(person);
    }

    @Override
    @RabbitListener(queues = "person.rpc.requests.findAll")
    public List<PersonDto> findAll(boolean bicycle) {
        return personRepository.findAll().stream()
                .map(PersonDto::convertToDto)
                .toList();
    }

    @Override
    @RabbitListener(queues = "person.rpc.requests.findAllByName")
    public List<PersonDto> findAll(String name) {
        return personRepository.findAllByName(name).stream()
                .map(PersonDto::convertToDto)
                .toList();
    }
}
