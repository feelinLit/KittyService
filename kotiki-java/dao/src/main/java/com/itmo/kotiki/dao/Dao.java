package com.itmo.kotiki.dao;

import com.itmo.kotiki.entity.BaseEntity;

import java.util.List;

public interface Dao<TEntity extends BaseEntity> {
    void persist(TEntity entity);

    void update(TEntity entity);

    TEntity findById(Long id);

    void delete(TEntity entity);

    List<TEntity> findAll();

    void deleteAll();
}
