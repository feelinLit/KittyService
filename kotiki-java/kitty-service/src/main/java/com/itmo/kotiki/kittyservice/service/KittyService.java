package com.itmo.kotiki.kittyservice.service;

import com.itmo.kotiki.dto.*;

import java.util.List;

public interface KittyService {

    KittyDto findById(Long id);

    void delete(Long id);

    List<KittyDto> findAll(FindAllKittiesDto dto);

    void addFriend(AddFriendDto dto);

    KittyDto save(KittyDto entity);

    KittyDto saveOrUpdate(KittyDto entity);

    List<KittyDto> findAll(FindByColorDto dto);

    List<KittyDto> findAll(FindByBreedDto dto);
}
