package com.took.egg_plant_project.warehouse;

import com.took.egg_plant_project.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    Optional<Warehouse> findBySector(String sector);

}




