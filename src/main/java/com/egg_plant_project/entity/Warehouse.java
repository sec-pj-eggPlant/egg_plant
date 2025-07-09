package com.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @Column(name = "warehouseID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(length = 10)
    private String sector;

    private Integer area;
    private Integer pricePerDay;

    @Column(length = 20)
    private String status;
}
