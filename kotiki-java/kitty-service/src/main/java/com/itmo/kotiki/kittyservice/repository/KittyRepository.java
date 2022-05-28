package com.itmo.kotiki.kittyservice.repository;

import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KittyRepository extends JpaRepository<Kitty, Long> {

    List<Kitty> findAllByPerson_UserUsername(String username);

    List<Kitty> findAllByBreed(String breed);

    List<Kitty> findAllByColor(Color color);

    List<Kitty> findAllByBreedAndPerson_User_Username(String breed, String user);

    List<Kitty> findAllByColorAndPerson_User_Username(Color color, String user);
}