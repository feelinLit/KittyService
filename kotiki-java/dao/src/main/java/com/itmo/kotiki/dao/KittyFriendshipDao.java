package com.itmo.kotiki.dao;

import com.itmo.kotiki.entity.KittyFriendship;

import java.util.List;

public class KittyFriendshipDao extends BaseDao<KittyFriendship> {
    @Override
    public KittyFriendship findById(Long id) {
        return getCurrentSession().get(KittyFriendship.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<KittyFriendship> findAll() {
        return (List<KittyFriendship>) getCurrentSession().createQuery("FROM KittyFriendship ").list();
    }
}
