package com.took.egg_plant_project.warehouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<com.egg_plant_project.entity.Warehouse, Integer> {
    Optional<Warehouse> findBySector(String sector);

}




