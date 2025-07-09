package com.took.egg_plant_project.warehouse;

import com.took.egg_plant_project.entity.Box;
import com.took.egg_plant_project.entity.WarehouseUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoxRepository extends JpaRepository<Box, Integer> {
// 기존: box 테이블에 sector 컬럼이 있었다면
// List<Box> findBySector(String sector);

    // 변경: box의 warehouse(외래키)와 조인해서 sector로 찾기
    @Query("SELECT b FROM Box b WHERE b.warehouse.sector = :sector")
    List<Box> findByWarehouseSector(@Param("sector") String sector);

}


