package com.took.egg_plant_project.constant;

import lombok.Getter;

@Getter
public enum TradeStatus {
    ACTIVE("거래가능"),
    IN_PROGRESS("거래중"),
    DONE("거래완료");

    private final String displayName;

    TradeStatus(String displayName) {
        this.displayName = displayName;
    }

    // 문자열 상태 코드로 Enum 객체 반환 (없으면 null or 기본값)
    public static TradeStatus fromCode(String code) {
        if (code == null) return null;
        try {
            return TradeStatus.valueOf(code);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
