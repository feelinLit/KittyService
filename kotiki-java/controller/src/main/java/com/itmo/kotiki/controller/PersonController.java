package com.itmo.kotiki.controller;

import com.itmo.kotiki.dto.PersonDto;
import com.itmo.kotiki.entity.Person;
import com.itmo.kotiki.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @ResponseBody
    public List<PersonDto> findAll() {
        return personService.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @GetMapping(value = "/byName/{name}")
    @ResponseBody
    public List<PersonDto> findAll(@PathVariable("name") String name) {
        return personService.findAll(name).stream()
                .map(this::convertToDto)
                .toList();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public PersonDto findById(@PathVariable("id") Long id) {
        return convertToDto(personService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto create(@RequestBody PersonDto personDto) {
        Person person = personService.save(convertToEntity(personDto));
        return convertToDto(person);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto updatePost(@PathVariable("id") Long id, @RequestBody PersonDto personDto) {
        if (!Objects.equals(id, personDto.getId()))
            throw new IllegalArgumentException("IDs don't match");
        Person person = convertToEntity(personDto);
        return convertToDto(personService.saveOrUpdate(id, person));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        personService.delete(id);
    }

    @PatchMapping(value = "/{personId}/addKitty")
    public void addKitty(@PathVariable("personId") Long personId, Long kittyId) {
        personService.addKitty(personId, kittyId);
    }

    private PersonDto convertToDto(Person person) {
        return new PersonDto(person.getId(), person.getName(), person.getUsername(), person.getPassword(), person.getRole(), person.getDateOfBirth());
    }

    private Person convertToEntity(PersonDto personDto) {
        return new Person(personDto.getName(), personDto.getUsername(), personDto.getPassword(), personDto.getRole(), personDto.getDateOfBirth(), null);
    }
}
