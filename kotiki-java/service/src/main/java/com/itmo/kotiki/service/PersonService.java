package com.itmo.kotiki.service;

import com.itmo.kotiki.dao.PersonDao;
import com.itmo.kotiki.entity.Person;

import java.util.List;

public class PersonService {
    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public void persist(Person entity) {
        personDao.openCurrentSessionWithTransaction();
        personDao.persist(entity);
        personDao.closeCurrentSessionWithTransaction();
    }

    public void update(Person entity) {
        personDao.openCurrentSessionWithTransaction();
        personDao.update(entity);
        personDao.closeCurrentSessionWithTransaction();
    }

    public Person findById(Long id) {
        personDao.openCurrentSession();
        Person person = personDao.findById(id);
        personDao.closeCurrentSession();
        return person;
    }

    public void delete(Long id) {
        personDao.openCurrentSessionWithTransaction();
        Person person = personDao.findById(id);
        personDao.delete(person);
        personDao.closeCurrentSessionWithTransaction();
    }

    public List<Person> findAll() {
        personDao.openCurrentSession();
        List<Person> persons = personDao.findAll();
        personDao.closeCurrentSession();
        return persons;
    }

    public void deleteAll() {
        personDao.openCurrentSessionWithTransaction();
        personDao.deleteAll();
        personDao.closeCurrentSessionWithTransaction();
    }
}
