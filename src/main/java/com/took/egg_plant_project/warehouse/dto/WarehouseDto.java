package com.took.egg_plant_project.warehouse.dto;

import com.took.egg_plant_project.entity.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseDto {
    private Integer id;
    private String sector;
    private Integer area;
    private Integer pricePerDay;


    public static WarehouseDto fromEntity(Warehouse w) {
        return new WarehouseDto(w.getId(), w.getSector(), w.getArea(), w.getPricePerDay());
    }
}
