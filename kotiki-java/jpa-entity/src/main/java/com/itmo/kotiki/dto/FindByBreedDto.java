package com.itmo.kotiki.dto;

import java.io.Serializable;

public record FindByBreedDto(String breed, String username) implements Serializable {
}
