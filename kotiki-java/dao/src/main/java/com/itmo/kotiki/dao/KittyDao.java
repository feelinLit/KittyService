package com.itmo.kotiki.dao;

import com.itmo.kotiki.entity.Kitty;

import java.util.List;

public class KittyDao extends BaseDao<Kitty> {

    @Override
    public Kitty findById(Long id) {
        return getCurrentSession().get(Kitty.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Kitty> findAll() {
        return (List<Kitty>) getCurrentSession().createQuery("FROM Kitty").list();
    }
}
