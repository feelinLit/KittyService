package com.itmo.kotiki.service.implementation;

import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.repository.KittyRepository;
import com.itmo.kotiki.repository.PersonRepository;
import com.itmo.kotiki.service.KittyService;
import com.itmo.kotiki.tool.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class KittyServiceImpl implements KittyService {

    private final KittyRepository kittyRepository;
    private final PersonRepository personRepository;

    @Autowired
    public KittyServiceImpl(KittyRepository kittyRepository, PersonRepository personRepository) {
        this.kittyRepository = kittyRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Kitty save(Kitty entity, String username) {
        entity.setPerson(personRepository.findByUsername(username));
        if (entity.getDateOfBirth() == null)
            entity.setDateOfBirth(LocalDate.now());
        return kittyRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public Kitty saveOrUpdate(Long id, Kitty entity, String username) {
        Kitty kitty = kittyRepository.findById(id)
                .map(kittyFound -> {
                    kittyFound.setName(entity.getName());
                    kittyFound.setBreed(entity.getBreed());
                    kittyFound.setColor(entity.getColor());
                    if (entity.getDateOfBirth() != null) kittyFound.setDateOfBirth(entity.getDateOfBirth());
                    return kittyFound;
                })
                .orElse(entity);
        return save(kitty, username);
    }

    @Override
    public Kitty findById(Long id) {
        return kittyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kitty wasn't found"));
    }

    @Override
    public void delete(Long id) {
        Kitty kitty = kittyRepository.findById(id)
                .orElseThrow(() -> new DomainException("Kitty wasn't found"));
        kittyRepository.delete(kitty);
    }

    @Override
    public List<Kitty> findAll() {
        return kittyRepository.findAll();
    }

    @Override
    public List<Kitty> findAll(Color color) {
        return kittyRepository.findAllByColor(color);
    }

    @Override
    public List<Kitty> findAll(String breed) {
        return kittyRepository.findAllByBreed(breed);
    }

    @Override
    public void deleteAll() {
        kittyRepository.deleteAll();
    }

    @Override
    public void addFriend(Long kittyId, Long friendId) {
        Kitty kitty = kittyRepository.findById(kittyId).orElseThrow(() -> new DomainException("Kitty wasn't found"));
        Kitty kittyFriend = kittyRepository.findById(friendId).orElseThrow(() -> new DomainException("Kitty-friend wasn't found"));
        kitty.addFriend(kittyFriend);
        kittyFriend.addFriend(kitty);
        save(kitty, kitty.getPerson().getUsername());
        save(kittyFriend, kittyFriend.getPerson().getUsername());
    }
}
