package com.itmo.kotiki.controller;

import com.itmo.kotiki.dto.KittyDto;
import com.itmo.kotiki.entity.Kitty;
import com.itmo.kotiki.service.KittyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @GetMapping
    @ResponseBody
    public List<KittyDto> findAll() {
        return kittyService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public KittyDto findById(@PathVariable("id") Long id) {
        return convertToDto(kittyService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KittyDto create(@RequestBody KittyDto kittyDto) {
        // Preconditions.checkNotNull(resource);
        Kitty kitty = kittyService.save(convertToEntity(kittyDto));
        return convertToDto(kitty);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KittyDto updatePost(@PathVariable("id") Long id, @RequestBody KittyDto kittyDto) { // TODO: DTO
//        Preconditions.checkNotNull(resource);
//        RestPreconditions.checkNotNull(service.getById(resource.getId()));
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

    private KittyDto convertToDto(Kitty kitty) {
        KittyDto kittyDto = modelMapper.map(kitty, KittyDto.class); // TODO: Enum mapping
        return kittyDto;
    }

    private Kitty convertToEntity(KittyDto kittyDto){
        Kitty kitty = modelMapper.map(kittyDto, Kitty.class); // TODO: Enum mapping
        return kitty;
    }
}
