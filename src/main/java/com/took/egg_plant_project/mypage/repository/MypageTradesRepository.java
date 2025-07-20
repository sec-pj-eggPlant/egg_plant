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
            SELECT t.TRADEID, t.STATUS, t.CREATEDAT,
                   renter.USERNAME AS renterName,
                   owner.USERNAME AS ownerName,
                   p.TITLE AS postTitle, p.LOCATION, p.PRICE
            FROM TRADE t
            JOIN MEMBER renter ON t.RENTERID = renter.MEMBERID
            JOIN MEMBER owner ON t.OWNERID = owner.MEMBERID
            JOIN POST p ON t.POSTID = p.POSTID
            WHERE 
                            
                                                             
                (
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
                                                           OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY
            """, nativeQuery = true)
    List<MypageTradesDto> searchTrades(
//            @Param("userId") int userId,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("offset") int offset,
            @Param("limit") int limit
    );
}
