package com.itmo.kotiki.service;

import com.itmo.kotiki.dao.KittyDao;
import com.itmo.kotiki.entity.Kitty;

import java.util.List;

public class KittyService {
    private static KittyDao kittyDao;

    public KittyService() {
        kittyDao = new KittyDao();
    }

    public void persist(Kitty entity) {
        kittyDao.openCurrentSessionWithTransaction();
        kittyDao.persist(entity);
        kittyDao.closeCurrentSessionWithTransaction();
    }

    public void update(Kitty entity) {
        kittyDao.openCurrentSessionWithTransaction();
        kittyDao.update(entity);
        kittyDao.closeCurrentSessionWithTransaction();
    }

    public Kitty findById(Long id) {
        kittyDao.openCurrentSession();
        Kitty kitty = kittyDao.findById(id);
        kittyDao.closeCurrentSession();
        return kitty;
    }

    public void delete(Long id) {
        kittyDao.openCurrentSessionWithTransaction();
        Kitty kitty = kittyDao.findById(id);
        kittyDao.delete(kitty);
        kittyDao.closeCurrentSessionWithTransaction();
    }

    public List<Kitty> findAll() {
        kittyDao.openCurrentSession();
        List<Kitty> kitties = kittyDao.findAll();
        kittyDao.closeCurrentSession();
        return kitties;
    }

    public void deleteAll() {
        kittyDao.openCurrentSessionWithTransaction();
        kittyDao.deleteAll();
        kittyDao.closeCurrentSessionWithTransaction();
    }

//    public KittyDao kittyDao() {
//        return kittyDao;
//    }
}
