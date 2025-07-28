package com.took.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @Column(name = "WAREHOUSEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String sector;

    private Integer area;
    private Integer pricePerDay;

    @OneToMany(mappedBy = "warehouse")
    private List<Box> boxes = new ArrayList<>();
}

