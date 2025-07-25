package com.took.egg_plant_project.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {

    IN_PROGRESS("거래중"),
    ACTIVE("거래가능"),
    DONE("거래완료");

    private final String label;
}
