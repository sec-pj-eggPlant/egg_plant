package com.took.egg_plant_project.warehouse.dto;


import com.took.egg_plant_project.entity.Warehouse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoxResponse {
    private Integer warehouseId;
    private String sector;
    private Integer area;
    private Integer pricePerDay;
    private String status;

    public BoxResponse(Warehouse w) {
        this.warehouseId = w.getId();
        this.sector = w.getSector();
        this.area = w.getArea();
        this.pricePerDay = w.getPricePerDay();
    }

    // getter 생략
}


