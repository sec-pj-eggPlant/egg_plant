package com.took.egg_plant_project.mypage.repository;

import com.took.egg_plant_project.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

//거래 내역 조회와 검색 기능
public interface MypageTradesRepository extends JpaRepository<Trade, Integer> {
    @Query(value = """
            
                        SELECT * FROM (
                SELECT 
                    t.TRADEID,           -- 거래ID
                    t.STATUS,            -- 거래상태
                    t.CREATEDAT,         -- 요청일
                    renter.USERNAME AS renterName,     -- 임차인 이름
                    owner.USERNAME AS ownerName,       -- 임대인 이름
                    p.TITLE,             -- 게시글/창고명
                    p.LOCATION,          -- 위치
                    p.PRICE,             -- 금액
                    ROW_NUMBER() OVER (ORDER BY t.CREATEDAT DESC) AS rn
                FROM TRADE t
                JOIN MEMBER renter ON t.RENTERID = renter.MEMBERID
                JOIN MEMBER owner ON t.OWNERID = owner.MEMBERID
                JOIN POST p ON t.POSTID = p.POSTID
                LEFT JOIN WAREHOUSE_USE wu 
                    ON wu.MEMBERID = t.RENTERID AND wu.WAREHOUSEID = p.POSTID
                WHERE (:startDate IS NULL OR t.CREATEDAT >= :startDate)
                  AND (:endDate IS NULL OR t.CREATEDAT <= :endDate)
                  AND (
                        (:searchType = 'title' AND (:keyword IS NULL OR p.TITLE LIKE CONCAT('%', :keyword, '%')))
                     OR (:searchType = 'location' AND (:keyword IS NULL OR p.LOCATION LIKE CONCAT('%', :keyword, '%')))
                     OR (:searchType = 'renterName' AND (:keyword IS NULL OR renter.USERNAME LIKE CONCAT('%', :keyword, '%')))
                     OR (:searchType = 'ownerName' AND (:keyword IS NULL OR owner.USERNAME LIKE CONCAT('%', :keyword, '%')))
                     OR (:searchType IS NULL)
                  )
            ) 
            WHERE rn BETWEEN :startRow AND :endRow
            """, nativeQuery = true)
    List<Object[]> searchTrades(
            @Param("searchType") String searchType,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("startRow") int startRow,
            @Param("endRow") int endRow
    );
}
