package com.itmo.kotiki.controller;

import com.itmo.kotiki.dto.KittyDto;
import com.itmo.kotiki.entity.Color;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.service.KittyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/kitty")
class KittyController {

    private final KittyService kittyService;
    private final ModelMapper modelMapper;

    @Autowired
    public KittyController(KittyService kittyService, ModelMapper modelMapper) {
        this.kittyService = kittyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/ByColor/{color}")
    @ResponseBody
    public List<KittyDto> findAllByColor(@PathVariable Color color) {
        return kittyService.findAll(color).stream()
                .map(this::convertToDto)
                .toList();
    }

    @GetMapping("/ByBreed/{breed}")
    @ResponseBody
    public List<KittyDto> findAllByBreed(@PathVariable String breed) {
        return kittyService.findAll(breed).stream()
                .map(this::convertToDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public KittyDto findById(@PathVariable("id") Long id) {
        return convertToDto(kittyService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KittyDto create(@RequestBody KittyDto kittyDto) {
        Kitty kitty = kittyService.save(convertToEntity(kittyDto));
        return convertToDto(kitty);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KittyDto updatePost(@PathVariable("id") Long id, @RequestBody KittyDto kittyDto) { // TODO: DTO
        if (!Objects.equals(id, kittyDto.getId()))
            throw new IllegalArgumentException("IDs don't match");
        Kitty kitty = convertToEntity(kittyDto);
        return convertToDto(kittyService.saveOrUpdate(id, kitty));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        kittyService.delete(id);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(Long kittyId, Long friendId) {
        kittyService.addFriend(kittyId, friendId);
    }

    private KittyDto convertToDto(Kitty kitty) {
        return modelMapper.map(kitty, KittyDto.class);
    }

    private Kitty convertToEntity(KittyDto kittyDto) {
        return modelMapper.map(kittyDto, Kitty.class);
    }
}
