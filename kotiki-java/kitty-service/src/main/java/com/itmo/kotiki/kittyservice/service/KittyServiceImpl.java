package com.itmo.kotiki.kittyservice.service;

import com.itmo.kotiki.dto.*;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.kittyservice.repository.KittyRepository;
import com.itmo.kotiki.kittyservice.tool.DomainException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class KittyServiceImpl implements KittyService {

    private final KittyRepository kittyRepository;

    @Autowired
    public KittyServiceImpl(KittyRepository kittyRepository) {
        this.kittyRepository = kittyRepository;
    }

    @Override
    @RabbitListener(queues = "kitty.rpc.requests.save")
    public KittyDto save(KittyDto entity) {
        var kitty = entity.convertToEntity();
        if (kitty.getDateOfBirth() == null)
            kitty.setDateOfBirth(LocalDate.now());
        kitty.getPerson().addKitty(kitty);
        return new KittyDto(kittyRepository.saveAndFlush(kitty));
    }

    @Override
    @RabbitListener(queues = "kitty.rpc.requests.saveOrUpdate")
    @Transactional
    public KittyDto saveOrUpdate(KittyDto entity) {
        Kitty kitty = kittyRepository.findById(entity.getId())
                .map(kittyFound -> {
                    kittyFound.setName(entity.getName());
                    kittyFound.setBreed(entity.getBreed());
                    kittyFound.setColor(entity.getColor());
                    if (entity.getDateOfBirth() != null) kittyFound.setDateOfBirth(entity.getDateOfBirth());
                    return kittyFound;
                })
                .orElse(entity.convertToEntity());
        kittyRepository.flush();
        return new KittyDto(kitty);
    }

    @Override
    @RabbitListener(queues = "kitty.rpc.requests.findById")
    public KittyDto findById(Long id) {
        return new KittyDto(
                kittyRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Kitty wasn't found")));
    }

    @Override
    @RabbitListener(queues = "kitty.rpc.requests.delete")
    public void delete(Long id) {
        Kitty kitty = kittyRepository.findById(id)
                .orElseThrow(() -> new DomainException("Kitty wasn't found"));
        kittyRepository.delete(kitty);
    }

    @Override
    @RabbitListener(queues = "kitty.rpc.requests.findAll")
    public List<KittyDto> findAll(FindAllKittiesDto dto) {
        if (dto.isAdmin())
            return kittyRepository.findAll().stream()
                    .map(KittyDto::new)
                    .toList();
        else
            return kittyRepository.findAllByPerson_UserUsername(dto.username()).stream()
                    .map(KittyDto::new)
                    .toList();
    }

    @Override
    @RabbitListener(queues = "kitty.rpc.requests.findAllByColor")
    public List<KittyDto> findAll(FindByColorDto dto) {
        if (dto.username() != null)
            return kittyRepository.findAllByColorAndPerson_User_Username(dto.color(), dto.username()).stream()
                    .map(KittyDto::new)
                    .toList();
        else
            return kittyRepository.findAllByColor(dto.color()).stream()
                    .map(KittyDto::new)
                    .toList();
    }

    @Override
    @RabbitListener(queues = "kitty.rpc.requests.findAllByBreed")
    public List<KittyDto> findAll(FindByBreedDto dto) {
        if (dto.username() != null)
            return kittyRepository.findAllByBreedAndPerson_User_Username(dto.breed(), dto.username()).stream()
                    .map(KittyDto::new)
                    .toList();
        else
            return kittyRepository.findAllByBreed(dto.breed()).stream()
                    .map(KittyDto::new)
                    .toList();
    }

    @Override
    @RabbitListener(queues = "kitty.rpc.requests.addFriend")
    public void addFriend(AddFriendDto dto) {
        Kitty kitty = kittyRepository.findById(dto.kittyId()).orElseThrow(() -> new DomainException("Kitty wasn't found"));
        Kitty kittyFriend = kittyRepository.findById(dto.friendId()).orElseThrow(() -> new DomainException("Kitty-friend wasn't found"));
        kitty.addFriend(kittyFriend);
        kittyFriend.addFriend(kitty);
        save(new KittyDto(kitty));
        save(new KittyDto(kittyFriend));
    }
}
