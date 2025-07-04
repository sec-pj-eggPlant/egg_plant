package com.took.egg_plant_project.warehouse;

import com.took.egg_plant_project.entity.WarehouseUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseUseRepository extends JpaRepository<WarehouseUse, Integer> {
    List<WarehouseUse> findByWarehouse_Sector(String sector);
    List<WarehouseUse> findByIdIn(List<Integer> ids);  // ID 목록으로 WarehouseUse 찾기
}
