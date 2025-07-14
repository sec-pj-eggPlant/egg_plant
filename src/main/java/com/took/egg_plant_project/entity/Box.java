package com.took.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "box")
public class Box {

    @Id
    @Column(name = "BOX_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAREHOUSEID", nullable = false)
    private Warehouse warehouse;

    @Column(name = "BOX_NUMBER", length = 10, nullable = false)
    private String boxNumber;
}

