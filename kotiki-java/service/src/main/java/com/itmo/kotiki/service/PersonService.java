package com.itmo.kotiki.service;

import com.itmo.kotiki.dao.PersonDao;
import com.itmo.kotiki.entity.Person;

import java.util.List;

public class PersonService {
    private static PersonDao personDao;

    public PersonService() {
        personDao = new PersonDao();
    }

    public void persist(Person entity) {
        personDao.openCurrentSessionWithTransaction();
        personDao.persist(entity);
        personDao.closeCurrentSessionWithTransaction();
    }

    public void update(Person entity) {
        personDao.openCurrentSessionWithTransaction();
        personDao.update(entity);
        personDao.openCurrentSessionWithTransaction();
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
        personDao.openCurrentSessionWithTransaction();
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
        personDao.openCurrentSessionWithTransaction();
    }

    public PersonDao personDao() {
        return personDao;
    }
}
