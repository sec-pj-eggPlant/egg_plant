package com.took.egg_plant_project.warehouse;

import com.took.egg_plant_project.entity.WarehouseUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WarehouseUseRepository extends JpaRepository<WarehouseUse, Integer> {

    /**
     * warehouse.id = :warehouseId 이고,
     * startDate ≤ :reqEndDate AND endDate ≥ :reqStartDate
     */
    boolean existsByBox_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Integer boxId,
            LocalDate reqEndDate,
            LocalDate reqStartDate
    );

}