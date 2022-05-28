package com.itmo.kotiki.dto;

import java.io.Serializable;

public record FindAllKittiesDto(String username, Boolean isAdmin) implements Serializable {
}
