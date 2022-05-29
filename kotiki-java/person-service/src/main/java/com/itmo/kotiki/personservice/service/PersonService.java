package com.itmo.kotiki.personservice.service;

import com.itmo.kotiki.dto.PersonDto;

import java.util.List;

public interface PersonService {

    PersonDto findById(Long id);

    void delete(Long id);

    List<PersonDto> findAll(boolean bicycle);

    PersonDto save(PersonDto entity);

    PersonDto saveOrUpdate(PersonDto entity);

    List<PersonDto> findAll(String name);

    PersonDto findByUsername(String username);
}
