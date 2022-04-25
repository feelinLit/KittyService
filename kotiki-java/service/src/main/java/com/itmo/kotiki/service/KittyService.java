package com.itmo.kotiki.service;

import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;

import java.util.List;

public interface KittyService extends Service<Kitty> {

    void addFriend(Long kittyId, Long friendId);

    Kitty save(Kitty kitty, String username);

    Kitty saveOrUpdate(Long id, Kitty entity, String username);

    List<Kitty> findAll(Color color, String username);

    List<Kitty> findAll(String breed, String username);

    List<Kitty> findAll(Color color);

    List<Kitty> findAll(String breed);
}
