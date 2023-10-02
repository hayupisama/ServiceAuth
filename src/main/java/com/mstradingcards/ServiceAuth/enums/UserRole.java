package com.mstradingcards.ServiceAuth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	
public enum UserRole {
    ADMIN("ADMIN"),
    GAME_MASTER("GAME_MASTER"),
    PLAYER("PLAYER"),
    GUEST("GUEST");
    @Getter
    private final String permission;
}
