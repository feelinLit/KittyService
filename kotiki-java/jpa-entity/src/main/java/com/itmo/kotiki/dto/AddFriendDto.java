package com.itmo.kotiki.dto;

import java.io.Serializable;

public record AddFriendDto(Long kittyId, Long friendId) implements Serializable {
}
