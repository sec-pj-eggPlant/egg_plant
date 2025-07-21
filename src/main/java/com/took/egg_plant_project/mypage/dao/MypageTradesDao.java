package com.took.egg_plant_project.mypage.dao;

import com.took.egg_plant_project.mypage.dto.MypageTradesDto;
import com.took.egg_plant_project.mypage.repository.MypageTradesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MypageTradesDao {
    private final MypageTradesRepository mypageTradesRepository;

    public List<MypageTradesDto> searchTrades(
            String searchType,
            String keyword,
            LocalDate startDate,
            LocalDate endDate,
            int offset,
            int limit
    ) {
        return mypageTradesRepository.searchTrades(
             searchType, keyword, startDate, endDate, offset, limit
        );
    }
}
