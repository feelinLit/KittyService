package com.itmo.kotiki.service.implementation;

import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.repository.KittyRepository;
import com.itmo.kotiki.service.KittyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class KittyServiceImpl implements KittyService {

    private final KittyRepository kittyRepository;

//    private final KittyFriendshipDao kittyFriendshipDao;

    @Autowired
    public KittyServiceImpl(KittyRepository kittyRepository) {
        this.kittyRepository = kittyRepository;
    }

    @Override
    public Kitty save(Kitty entity) {
        if (entity.getDateOfBirth() == null)
            entity.setDateOfBirth(LocalDate.now());
        return kittyRepository.saveAndFlush(entity);
    }

    @Override
    public Kitty saveOrUpdate(Long id, Kitty entity) {
        Kitty kitty = kittyRepository.findById(id)
                .map(kittyFound -> {
                    kittyFound.setName(entity.getName());
                    kittyFound.setBreed(entity.getBreed());
                    kittyFound.setColor(entity.getColor());
                    if (entity.getDateOfBirth() != null) kittyFound.setDateOfBirth(entity.getDateOfBirth());
                    return kittyFound;})
                .orElse(entity);
        return save(kitty);
    }

    @Override
    public Kitty findById(Long id) {
        return kittyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kitty wasn't found"));
    }

    @Override
    public void delete(Long id) {
        Kitty kitty = kittyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kitty wasn't found"));
        kittyRepository.delete(kitty);
    }

    @Override
    public List<Kitty> findAll() {
        return kittyRepository.findAll();
    }

    @Override
    public void deleteAll() {
        kittyRepository.deleteAll();
    }

//    public void addFriendship(Long kitty1, Long kitty2) {
//        kittyFriendshipDao.openCurrentSessionWithTransaction();
//        var friendship1 = kitty1.addFriend(kitty2);
//        var friendship2 = kitty2.addFriend(kitty1);
//        kittyFriendshipDao.persist(friendship1);
//        kittyFriendshipDao.persist(friendship2);
//        kittyFriendshipDao.closeCurrentSessionWithTransaction();
//    }
}
