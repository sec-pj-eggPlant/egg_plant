package com.took.egg_plant_project.admin.repository;

import com.took.egg_plant_project.entity.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
ORDER BY t.createdAt DESC
""")
    Page<Trade> findFilteredTrades(
            @Param("status") String status,
            @Param("renterId") String renterId,
            @Param("ownerId") String ownerId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @Query("SELECT COUNT(t) FROM Trade t WHERE UPPER(t.status) = 'DONE'")
    long countByStatusDone();

    @Query(value = """
    SELECT TO_NUMBER(TO_CHAR(t.createdAt, 'MM')) AS month,
           COUNT(*) AS count
    FROM trade t
    WHERE t.status = 'DONE'
    GROUP BY TO_NUMBER(TO_CHAR(t.createdAt, 'MM'))
    ORDER BY month
    """, nativeQuery = true)
    List<Object[]> countDoneTradesByMonth();
}