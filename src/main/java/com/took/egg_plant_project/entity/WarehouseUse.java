package com.took.egg_plant_project.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "warehouse_use")
public class WarehouseUse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouseId")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member user;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(length = 20)
    private String status; // 예: 예약 완료, 취소 등
}

