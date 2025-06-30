package com.took.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String sector; // 예: A, B, C 등

    private Integer area; // 면적 (예: 5㎡)

    private Integer pricePerDay;

    @Column(length = 20)
    private String status; // 예: ACTIVE, INACTIVE 등
}
