package com.took.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @Column(name = "WAREHOUSEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(length = 10, nullable = false)
    private String sector;

    private Integer area;
    private Integer pricePerDay;

    // 필요시
    @OneToMany(mappedBy = "warehouse")
    private List<Box> boxes = new ArrayList<>();
}

