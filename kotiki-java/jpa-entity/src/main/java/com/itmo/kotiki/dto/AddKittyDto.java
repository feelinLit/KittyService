package com.itmo.kotiki.dto;

import java.io.Serializable;

public record AddKittyDto(Long personId, Long kittyId) implements Serializable {
}
