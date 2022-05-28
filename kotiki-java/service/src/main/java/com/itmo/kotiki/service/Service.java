package com.itmo.kotiki.service;

import com.itmo.kotiki.entity.BaseEntity;

import java.util.List;

public interface Service<T extends BaseEntity> {

    T findById(Long id);

    void delete(Long id);

    List<T> findAll();

    void deleteAll();
}
