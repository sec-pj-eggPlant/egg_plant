package com.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouse_use")
public class WarehouseUse {

    @Id
    @Column(name = "warehouseUseID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "warehouseID", nullable = false, referencedColumnName = "warehouseID")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "memberID", nullable = false, referencedColumnName = "memberID")
    private Member user;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length = 20)
    private String status;
}

