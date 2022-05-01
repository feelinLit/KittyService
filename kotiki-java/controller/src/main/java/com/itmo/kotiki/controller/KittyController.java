package com.itmo.kotiki.controller;

import com.itmo.kotiki.dto.KittyDto;
import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.entity.Role;
import com.itmo.kotiki.service.KittyService;
import com.itmo.kotiki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/kitty")
class KittyController {

    private final KittyService kittyService;
    private final UserService userService;

    @Autowired
    public KittyController(KittyService kittyService, UserService userService) {
        this.kittyService = kittyService;
        this.userService = userService;
    }

    @GetMapping("/byColor/{color}")
    @ResponseBody
    public List<KittyDto> findAllByColor(@PathVariable Color color, Authentication authentication) {
        var user = userService.loadUserByUsername(authentication.getName());
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(Role.ROLE_ADMIN.name()));
        if (isAdmin)
            return kittyService.findAll(color).stream()
                    .map(this::convertToDto)
                    .toList();
        return kittyService.findAll(color, user.getUsername()).stream()
                .map(this::convertToDto)
                .toList();
    }

    @GetMapping("/byBreed/{breed}")
    @ResponseBody
    public List<KittyDto> findAllByBreed(@PathVariable String breed, Authentication authentication) {
        var user = userService.loadUserByUsername(authentication.getName());
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(Role.ROLE_ADMIN.name()));
        if (isAdmin)
            return kittyService.findAll(breed).stream()
                    .map(this::convertToDto)
                    .toList();
        return kittyService.findAll(breed, user.getUsername()).stream()
                .map(this::convertToDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public KittyDto findById(@PathVariable("id") Long id, Authentication authentication) {
        var user = userService.loadUserByUsername(authentication.getName());
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(Role.ROLE_ADMIN.name()));
        var kitty = kittyService.findById(id);
        if (isAdmin || !Objects.equals(kitty.getPerson().getUser().getUsername(), authentication.getName()))
            return null;
        return convertToDto(kitty);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KittyDto create(@RequestBody KittyDto kittyDto, Authentication authentication) {
        Kitty kitty = kittyService.save(convertToEntity(kittyDto), authentication.getName());
        return convertToDto(kitty);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KittyDto updatePost(@PathVariable("id") Long id, @RequestBody KittyDto kittyDto, Authentication authentication) {
        if (!Objects.equals(id, kittyDto.getId()))
            throw new IllegalArgumentException("IDs don't match");
        var user = userService.loadUserByUsername(authentication.getName());
        var kitty = kittyService.findById(id);
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(Role.ROLE_ADMIN.name()));
        if (isAdmin || !Objects.equals(kitty.getPerson().getUser().getUsername(), authentication.getName()))
            throw new IllegalAccessError("Person doesnt own this cat");
        return convertToDto(kittyService.saveOrUpdate(id, convertToEntity(kittyDto), authentication.getName()));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id, Authentication authentication) {
        var user = userService.loadUserByUsername(authentication.getName());
        var kitty = kittyService.findById(id);
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(Role.ROLE_ADMIN.name()));
        if (isAdmin || !Objects.equals(kitty.getPerson().getUser().getUsername(), authentication.getName()))
            throw new IllegalAccessError("Person doesnt own this cat");
        kittyService.delete(id);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(Long kittyId, Long friendId, Authentication authentication) {
        kittyService.addFriend(kittyId, friendId);
    }

    private KittyDto convertToDto(Kitty kitty) {
        return new KittyDto(kitty.getId(), kitty.getName(), kitty.getBreed(), kitty.getColor(), kitty.getDateOfBirth());
    }

    private Kitty convertToEntity(KittyDto kittyDto) {
        return new Kitty(kittyDto.getBreed(), kittyDto.getName(), kittyDto.getColor(), kittyDto.getDateOfBirth(), null);
    }
}
