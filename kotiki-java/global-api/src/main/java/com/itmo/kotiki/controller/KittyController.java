package com.itmo.kotiki.controller;

import com.itmo.kotiki.dto.*;
import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.entity.Role;
import com.itmo.kotiki.security.user.service.UserService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/kitty")
class KittyController {

    private final RabbitTemplate template;
    private final DirectExchange exchange;
    private final UserService userService;

    @Autowired
    public KittyController(RabbitTemplate template, DirectExchange kittyExchange, UserService userService) {
        this.template = template;
        this.exchange = kittyExchange;
        this.userService = userService;
    }

    @GetMapping()
    @ResponseBody
    public List<KittyDto> findAll(Authentication authentication) {
        var user = userService.loadUserByUsername(authentication.getName());
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(Role.ROLE_ADMIN.name()));
        var dto = new FindAllKittiesDto(user.getUsername(), isAdmin);
        return (List<KittyDto>) template.convertSendAndReceive(exchange.getName(), "findAll", dto);
    }

    @GetMapping("/byColor/{color}")
    @ResponseBody
    public List<KittyDto> findAllByColor(@PathVariable Color color, Authentication authentication) {
        var user = userService.loadUserByUsername(authentication.getName());
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(Role.ROLE_ADMIN.name()));
        var findAllByColorDto = new FindByColorDto(color, isAdmin ? null : user.getUsername());
        return (List<KittyDto>) template.convertSendAndReceive(exchange.getName(), "findAllByColor", findAllByColorDto);
    }

    @GetMapping("/byBreed/{breed}")
    @ResponseBody
    public List<KittyDto> findAllByBreed(@PathVariable String breed, Authentication authentication) {
        var user = userService.loadUserByUsername(authentication.getName());
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(Role.ROLE_ADMIN.name()));
        var findAllByBreedrDto = new FindByBreedDto(breed, isAdmin ? null : user.getUsername());
        return (List<KittyDto>) template.convertSendAndReceive(exchange.getName(), "findAllByBreed", findAllByBreedrDto);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public KittyDto findById(@PathVariable("id") Long id, Authentication authentication) {
        var user = userService.loadUserByUsername(authentication.getName());
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(Role.ROLE_ADMIN.name()));
        var kitty = (Kitty) template.convertSendAndReceive(exchange.getName(), "findById", id);
        if (isAdmin || !Objects.equals(kitty.getPerson().getUser().getUsername(), authentication.getName()))
            return null;
        return new KittyDto(kitty);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KittyDto create(@RequestBody KittyDto kittyDto, Authentication authentication) {
        Kitty kitty = (Kitty) template.convertSendAndReceive(exchange.getName(), "save", kittyDto);
        return new KittyDto(kitty);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KittyDto updatePost(@PathVariable("id") Long id, @RequestBody KittyDto kittyDto, Authentication authentication) {
        if (!Objects.equals(id, kittyDto.getId()))
            throw new IllegalArgumentException("IDs don't match");
        var user = userService.loadUserByUsername(authentication.getName());
        var kitty = (Kitty) template.convertSendAndReceive(exchange.getName(), "findById", id);
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(Role.ROLE_ADMIN.name()));
        if (isAdmin || !Objects.equals(kitty.getPerson().getUser().getUsername(), authentication.getName()))
            throw new IllegalAccessError("Person doesnt own this cat");
        return (KittyDto) template.convertSendAndReceive(exchange.getName(), "saveOrUpdate", kittyDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id, Authentication authentication) {
        var user = userService.loadUserByUsername(authentication.getName());
        var kitty = (Kitty) template.convertSendAndReceive(exchange.getName(), "findById", id);
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(Role.ROLE_ADMIN.name()));
        if (isAdmin || !Objects.equals(kitty.getPerson().getUser().getUsername(), authentication.getName()))
            throw new IllegalAccessError("Person doesnt own this cat");
        template.convertAndSend(exchange.getName(), "delete", id);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(AddFriendDto addFriendDto) {
        template.convertAndSend(exchange.getName(), "addFriend", addFriendDto);
    }
}
