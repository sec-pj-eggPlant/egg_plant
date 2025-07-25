package com.took.egg_plant_project.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {
    REQUESTED("신청됨"),
    RECEIVED("창고에 도착"),
    SHIPPED("배송중"),
    DELIVERED("배송완료") ;
    private final String label;
}
