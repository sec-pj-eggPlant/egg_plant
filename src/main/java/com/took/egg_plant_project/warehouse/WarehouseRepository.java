package com.took.egg_plant_project.warehouse;

import com.took.egg_plant_project.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    List<Warehouse> findBySector(String sector);
}




