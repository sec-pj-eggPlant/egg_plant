package com.took.egg_plant_project.warehouse.dto;

import com.took.egg_plant_project.entity.WarehouseUse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WarehouseUseDto {
    private final Integer id;
    private final String status;
    private final String sector;

    // WarehouseUse 엔티티를 Dto로 변환
    public static WarehouseUseDto fromEntity(WarehouseUse use) {
        return new WarehouseUseDto(use.getId(), use.getStatus(), use.getWarehouse().getSector());
    }
}
