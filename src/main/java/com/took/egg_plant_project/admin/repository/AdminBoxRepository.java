package com.took.egg_plant_project.admin.repository;

import com.took.egg_plant_project.entity.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminBoxRepository extends JpaRepository<Box, Integer> {
    @Query("SELECT b FROM Box b JOIN FETCH b.warehouse")
    List<Box> findAllWithWarehouse();

    List<Box> findByWarehouse_Sector(String sector);

    int countByWarehouse_Sector(String sector); //
}
