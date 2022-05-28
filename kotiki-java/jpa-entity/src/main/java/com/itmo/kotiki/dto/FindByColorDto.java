package com.itmo.kotiki.dto;

import com.itmo.kotiki.entity.Color;

import java.io.Serializable;

public record FindByColorDto(Color color, String username) implements Serializable {
}

