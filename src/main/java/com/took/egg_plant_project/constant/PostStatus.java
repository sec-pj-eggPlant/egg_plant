package com.took.egg_plant_project.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {

    ACTIVATE("거래가능"),
    ROLE_RENTER("거래중"),
    ROLE_ADMIN("거래완료");

    private final String label;
}
