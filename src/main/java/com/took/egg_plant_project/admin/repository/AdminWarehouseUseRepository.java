package com.took.egg_plant_project.admin.repository;

import com.took.egg_plant_project.entity.Box;
import com.took.egg_plant_project.entity.WarehouseUse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AdminWarehouseUseRepository extends JpaRepository<WarehouseUse, Integer> {
    @Query("SELECT w FROM WarehouseUse w " +
            "WHERE :today BETWEEN w.startDate AND w.endDate")
    List<WarehouseUse> findActiveUses(@Param("today") LocalDate today);

    @Query("SELECT w FROM WarehouseUse w " +
            "WHERE w.box.id = :boxId ORDER BY w.endDate DESC")
    Optional<WarehouseUse> findLatestByBoxId(@Param("boxId") Long boxId);

    @Query("SELECT COUNT(w) > 0 FROM WarehouseUse w " +
            "WHERE w.box = :box AND :today BETWEEN w.startDate AND w.endDate")
    boolean isRented(@Param("box") Box box, @Param("today") LocalDate today);

    @Query("SELECT w FROM WarehouseUse w WHERE w.box.id = :boxId " +
            "AND (:keyword IS NULL OR w.user.userName LIKE CONCAT('%', :keyword, '%')) " +
            "AND (:startDate IS NULL OR w.endDate >= :startDate) " +
            "AND (:endDate IS NULL OR w.startDate <= :endDate)")
    Page<WarehouseUse> findByBoxWithFilters(@Param("boxId") Long boxId,
                                            @Param("keyword") String keyword,
                                            @Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate,
                                            Pageable pageable);

    @Query("SELECT COUNT(w) FROM WarehouseUse w " +
            "WHERE w.box.warehouse.sector = :sector " +
            "AND :today BETWEEN w.startDate AND w.endDate")
    int countBySectorAndDate(@Param("sector") String sector, @Param("today") LocalDate today);

}
