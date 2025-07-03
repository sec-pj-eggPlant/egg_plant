package com.took.egg_plant_project.admin.repository;

import com.took.egg_plant_project.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AdminTradeRepository extends JpaRepository<Trade, Integer> {

    @Query("""
    SELECT t FROM Trade t
    WHERE (:status IS NULL OR t.status = :status)
      AND (:renterId IS NULL OR t.renter.userID LIKE %:renterId%)
      AND (:ownerId IS NULL OR t.owner.userID LIKE %:ownerId%)
      AND (:start IS NULL OR t.createdAt >= :start)
      AND (:end IS NULL OR t.createdAt <= :end)
""")
    List<Trade> findFilteredTrades(
            @Param("status") String status,
            @Param("renterId") String renterId,
            @Param("ownerId") String ownerId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}