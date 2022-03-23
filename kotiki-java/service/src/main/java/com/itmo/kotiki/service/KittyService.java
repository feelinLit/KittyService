package com.itmo.kotiki.service;

import com.itmo.kotiki.dao.KittyDao;
import com.itmo.kotiki.dao.KittyFriendshipDao;
import com.itmo.kotiki.entity.Kitty;

import java.util.List;

public class KittyService {
    private final KittyDao kittyDao;
    private final KittyFriendshipDao kittyFriendshipDao;

    public KittyService(KittyDao kittyDao, KittyFriendshipDao kittyFriendshipDao) {
        this.kittyDao = kittyDao;
        this.kittyFriendshipDao = kittyFriendshipDao;
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

    public void addFriendship(Kitty kitty1, Kitty kitty2) {
        kittyFriendshipDao.openCurrentSessionWithTransaction();
        var friendship1 = kitty1.addFriend(kitty2);
        var friendship2 = kitty2.addFriend(kitty1);
        kittyFriendshipDao.persist(friendship1);
        kittyFriendshipDao.persist(friendship2);
        kittyFriendshipDao.closeCurrentSessionWithTransaction();
    }
}
