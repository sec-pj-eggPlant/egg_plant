package com.took.egg_plant_project.mypage.dao;

import com.took.egg_plant_project.mypage.repository.MypageTradesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MypageTradesDao {
    private final MypageTradesRepository mypageTradesRepository;

    public List<Object[]> searchTrades(String status, LocalDateTime startDate, LocalDateTime endDate, int page, int pageSize) {
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        return mypageTradesRepository.searchTrades(status, startDate, endDate, startRow, endRow);
    }
}
