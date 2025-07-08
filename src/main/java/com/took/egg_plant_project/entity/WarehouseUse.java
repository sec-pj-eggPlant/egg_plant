package com.took.egg_plant_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "warehouse_use")
@SequenceGenerator(
        name = "warehouse_use_seq_gen",
        sequenceName = "WAREHOUSE_USE_SEQ",
        allocationSize = 1
)
public class WarehouseUse {

    @Id
    @Column(name = "WAREHOUSEUSEID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "warehouse_use_seq_gen"
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOX_ID", nullable = false)
    private Box box;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WAREHOUSEID", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBERID", nullable = false)
    private Member user;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    public static WarehouseUse create(Box box, Member user, LocalDate startDate, LocalDate endDate) {
        WarehouseUse use = new WarehouseUse();
        use.box = box;
        use.user = user;
        use.startDate = startDate;
        use.endDate = endDate;
        // 반드시 아래 줄 필요!
        use.warehouse = box.getWarehouse();
        return use;
    }

}
