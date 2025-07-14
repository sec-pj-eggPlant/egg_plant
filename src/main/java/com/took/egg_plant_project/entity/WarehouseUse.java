package com.took.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Table(name = "warehouse_use")
public class WarehouseUse {

    @Id
    private Integer id;

    private Warehouse warehouse;

    private Member user;

    private LocalDate startDate;
    private LocalDate endDate;

}

