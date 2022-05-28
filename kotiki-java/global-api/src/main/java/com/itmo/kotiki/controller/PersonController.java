package com.itmo.kotiki.controller;

import com.itmo.kotiki.dto.PersonDto;
import com.itmo.kotiki.dto.UserDto;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final RabbitTemplate template;
    private final DirectExchange exchange;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonController(RabbitTemplate template, DirectExchange personExchange, PasswordEncoder passwordEncoder) {
        this.template = template;
        this.exchange = personExchange;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @ResponseBody
    public List<PersonDto> findAll() {
        return (List<PersonDto>) template.convertSendAndReceive(exchange.getName(), "findAll", true);
    }

    @GetMapping(value = "/byName/{name}")
    @ResponseBody
    public List<PersonDto> findAll(@PathVariable("name") String name) {
        return (List<PersonDto>) template.convertSendAndReceive(exchange.getName(), "findAllByName", name);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public PersonDto findById(@PathVariable("id") Long id) {
        return (PersonDto) template.convertSendAndReceive(exchange.getName(), "findById", id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto create(@RequestBody PersonDto personDto) {
        var userDto = personDto.getUser();
        var encodedPassword = passwordEncoder.encode(userDto.getPassword());
        var userDtoWithEncodedPassword = new UserDto(userDto.getId(), userDto.getUsername(), encodedPassword, userDto.getRole());
        var personDtoWithEncodedPassword = new PersonDto(personDto.getId(), personDto.getName(), personDto.getDateOfBirth(), userDto);
        return (PersonDto) template.convertSendAndReceive(exchange.getName(), "save", personDtoWithEncodedPassword);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto updatePost(@PathVariable("id") Long id, @RequestBody PersonDto personDto) {
        if (!Objects.equals(id, personDto.getId()))
            throw new IllegalArgumentException("IDs don't match");
        var userDto = personDto.getUser();
        var encodedPassword = passwordEncoder.encode(userDto.getPassword());
        var userDtoWithEncodedPassword = new UserDto(userDto.getId(), userDto.getUsername(), encodedPassword, userDto.getRole());
        var personDtoWithEncodedPassword = new PersonDto(personDto.getId(), personDto.getName(), personDto.getDateOfBirth(), userDto);
        return (PersonDto) template.convertSendAndReceive(exchange.getName(), "saveOrUpdate", personDtoWithEncodedPassword);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        template.convertAndSend(exchange.getName(), "delete", id);
    }
}
