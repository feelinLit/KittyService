package com.itmo.kotiki.repository;

import com.itmo.kotiki.entity.Kitty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KittyRepository extends JpaRepository<Kitty, Long> {
}