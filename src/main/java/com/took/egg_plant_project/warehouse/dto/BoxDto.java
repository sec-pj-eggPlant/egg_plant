package com.took.egg_plant_project.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoxDto {

    private Integer id;
    private String sector;
    private String boxNumber;
    private String status;
}
