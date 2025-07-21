package com.took.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "warehouse_use")
public class WarehouseUse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member user;

    private LocalDate startDate;
    private LocalDate endDate;

    public static WarehouseUse create(Box box, Member user, LocalDate start, LocalDate end) {
        WarehouseUse use = new WarehouseUse();
        use.warehouse = box.getWarehouse();
        use.user = user;
        use.startDate = start;
        use.endDate = end;
        return use;
    }
}
