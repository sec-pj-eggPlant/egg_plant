package com.took.egg_plant_project.admin.repository;

import com.took.egg_plant_project.admin.dto.AdminTradeSummaryDto;
import com.took.egg_plant_project.entity.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AdminTradeRepository extends JpaRepository<Trade, Integer> {

    @Query(value = """
    SELECT
        t.tradeID AS id,
        t.postID AS postId,
        r.userID AS renterId,
        o.userID AS ownerId,
        t.status AS status,
        t.createdAt AS createdAt
    FROM trade t
    LEFT JOIN member r ON t.renterID = r.memberID
    LEFT JOIN member o ON t.ownerID = o.memberID
    WHERE (:status IS NULL OR t.status = :status)
      AND (:renterId IS NULL OR r.userID LIKE %:renterId%)
      AND (:ownerId IS NULL OR o.userID LIKE %:ownerId%)
      AND (:start IS NULL OR t.createdAt >= :start)
      AND (:end IS NULL OR t.createdAt <= :end)
    ORDER BY t.createdAt DESC
    """,
            countQuery = """
    SELECT COUNT(*) FROM trade t
    LEFT JOIN member r ON t.renterID = r.memberID
    LEFT JOIN member o ON t.ownerID = o.memberID
    WHERE (:status IS NULL OR t.status = :status)
      AND (:renterId IS NULL OR r.userID LIKE %:renterId%)
      AND (:ownerId IS NULL OR o.userID LIKE %:ownerId%)
      AND (:start IS NULL OR t.createdAt >= :start)
      AND (:end IS NULL OR t.createdAt <= :end)
    """,
            nativeQuery = true)
    Page<Object[]> findRawTradeSummaryObjects(
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