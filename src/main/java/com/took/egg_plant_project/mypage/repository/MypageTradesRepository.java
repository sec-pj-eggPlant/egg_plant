package com.took.egg_plant_project.mypage.repository;

import com.took.egg_plant_project.entity.Trade;
import com.took.egg_plant_project.mypage.dto.MypageTradesDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//거래 내역 조회와 검색 기능
public interface MypageTradesRepository extends JpaRepository<Trade, Integer> {
    @Query(value = """
                SELECT COUNT(*) 
                FROM TRADE t
                WHERE (t.OWNERID = :memberId OR t.RENTERID = :memberId)
                  AND (
                       :searchType IS NULL OR :searchType = '' OR
                       (
                         (:searchType = 'status' AND (:keyword IS NULL OR t.STATUS = :keyword))
                         OR (:searchType = 'title' AND (:keyword IS NULL OR EXISTS (
                             SELECT 1 FROM POST p WHERE p.POSTID = t.POSTID AND p.TITLE LIKE '%' || :keyword || '%'
                         )))
                         OR (:searchType = 'location' AND (:keyword IS NULL OR EXISTS (
                             SELECT 1 FROM POST p WHERE p.POSTID = t.POSTID AND p.LOCATION LIKE '%' || :keyword || '%'
                         )))
                       )
                    )
                  AND (:startDate IS NULL OR t.CREATEDAT >= :startDate)
                  AND (:endDate IS NULL OR t.CREATEDAT <= :endDate)
            """, nativeQuery = true)
    int countTrades(
            @Param("memberId") Integer memberId,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query(value = """
                SELECT inner_query.*, ROWNUM rnum FROM (
                  SELECT t.TRADEID AS tradeId, t.STATUS AS status, t.CREATEDAT AS createDate,
                         renter.USERNAME AS renterName,
                         owner.USERNAME AS ownerName,
                         p.TITLE AS postTitle, p.LOCATION AS location, p.PRICE AS price
                  FROM TRADE t
                  JOIN MEMBER renter ON t.RENTERID = renter.MEMBERID
                  JOIN MEMBER owner ON t.OWNERID = owner.MEMBERID
                  JOIN POST p ON t.POSTID = p.POSTID
                  WHERE (t.OWNERID = :memberId OR t.RENTERID = :memberId)
                    AND (
                         :searchType IS NULL OR :searchType = '' OR
                         (
                           (:searchType = 'status' AND (:keyword IS NULL OR t.STATUS = :keyword))
                           OR (:searchType = 'title' AND (:keyword IS NULL OR p.TITLE LIKE '%' || :keyword || '%'))
                           OR (:searchType = 'location' AND (:keyword IS NULL OR p.LOCATION LIKE '%' || :keyword || '%'))
                         )
                      )
                    AND (:startDate IS NULL OR t.CREATEDAT >= :startDate)
                    AND (:endDate IS NULL OR t.CREATEDAT <= :endDate)
                  ORDER BY t.CREATEDAT DESC
                ) inner_query
                WHERE ROWNUM <= :endRow
            """, nativeQuery = true)
    List<Object[]> searchTradesRaw(
            @Param("memberId") Integer memberId,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startRow") int startRow,
            @Param("endRow") int endRow
    );
}

//        OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY