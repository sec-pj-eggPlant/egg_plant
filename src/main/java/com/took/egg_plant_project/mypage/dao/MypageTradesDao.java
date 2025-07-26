package com.took.egg_plant_project.mypage.dao;

import com.took.egg_plant_project.constant.TradeStatus;
import com.took.egg_plant_project.mypage.dto.MypageTradesDto;
import com.took.egg_plant_project.mypage.repository.MypageTradesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MypageTradesDao {
    private final MypageTradesRepository mypageTradesRepository;

    public List<MypageTradesDto> searchTrades(
            Integer memberId,
            String searchType,
            String keyword,
            LocalDate startDate,
            LocalDate endDate,
            int startRow,
            int endRow
    ) {
        List<Object[]> rows = mypageTradesRepository.searchTradesRaw(memberId, searchType, keyword, startDate, endDate, startRow, endRow
        );

        return rows.stream()
                .map(row-> new MypageTradesDto(
                ((Number) row[0]).intValue(),
                TradeStatus.fromCode((String) row[1]),
                (Timestamp) row[2],
                (String) row[3],
                (String) row[4],
                (String) row[5],
                (String) row[6],
                ((Number) row[7]).intValue()
        )).toList();
    }
}
