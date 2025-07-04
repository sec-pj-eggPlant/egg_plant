package com.took.egg_plant_project.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouseId")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberID", referencedColumnName = "memberID")
    private Member user;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length = 20)
    private String status;

    public static WarehouseUse create(Warehouse warehouse, Member user, LocalDate startDate, LocalDate endDate) {
        WarehouseUse use = new WarehouseUse();
        use.warehouse = warehouse;
        use.user = user;
        use.startDate = startDate;
        use.endDate = endDate;
        use.status = "RENTED";
        return use;
    }

    // 상태 변경 메서드
    public WarehouseUse rent(Member user, LocalDate start, LocalDate end) {
        if ("RENTED".equals(this.status)) {
            throw new IllegalStateException("이미 대여 중입니다.");
        }
        this.user = user;
        this.startDate = start;
        this.endDate = end;
        this.status = "RENTED";
        return this;
    }

    public void rent(LocalDate startDate, LocalDate endDate) {
        this.status = "RENTED";
        this.startDate = startDate;
        this.endDate = endDate;
    }

}

