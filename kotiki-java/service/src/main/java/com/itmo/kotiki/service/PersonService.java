package com.itmo.kotiki.service;

import com.itmo.kotiki.entity.Person;
import com.itmo.kotiki.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface PersonService extends Service<Person> {
}
