package com.itmo.kotiki.controller;

import com.itmo.kotiki.dto.PersonDto;
import com.itmo.kotiki.entity.Person;
import com.itmo.kotiki.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(PersonService personService, ModelMapper modelMapper) {
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public List<PersonDto> findAll() {
        return personService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public PersonDto findById(@PathVariable("id") Long id) {
        return convertToDto(personService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto create(@RequestBody PersonDto personDto) {
        // Preconditions.checkNotNull(resource);
        Person person = personService.save(convertToEntity(personDto));
        return convertToDto(person);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto updatePost(@PathVariable("id") Long id, @RequestBody PersonDto personDto) { // TODO: DTO
//        Preconditions.checkNotNull(resource);
//        RestPreconditions.checkNotNull(service.getById(resource.getId()));
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

    private PersonDto convertToDto(Person person) {
        PersonDto personDto = modelMapper.map(person, PersonDto.class); // TODO: Enum mapping
        return personDto;
    }

    private Person convertToEntity(PersonDto personDto){
        Person person = modelMapper.map(personDto, Person.class); // TODO: Enum mapping
        return person;
    }
}
