package com.egg_plant_project.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ROLE_OWNER("임차인"),
    ROLE_RENTER("임대인"),
    ROLE_ADMIN("관리자");
    private final String label;
}
