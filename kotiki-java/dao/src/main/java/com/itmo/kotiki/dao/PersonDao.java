package com.itmo.kotiki.dao;

import com.itmo.kotiki.entity.Person;

import java.util.List;

public class PersonDao extends BaseDao<Person> {
    @Override
    public Person findById(Long id) {
        return getCurrentSession().get(Person.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> findAll() {
        return (List<Person>) getCurrentSession().createQuery("FROM Person").list();
    }
}
