package com.itmo.kotiki.repository;

import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KittyRepository extends JpaRepository<Kitty, Long> {
    List<Kitty> findAllByBreed(String breed);

    List<Kitty> findAllByColor(Color color);
}