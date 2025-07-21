package com.took.egg_plant_project.mypage.service;

import com.took.egg_plant_project.mypage.dao.MypageTradesDao;
import com.took.egg_plant_project.mypage.dto.MypageTradesDto;
import com.took.egg_plant_project.mypage.repository.MypageTradesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageTradesService {
    private final MypageTradesDao mypageTradesDao;

    public List<MypageTradesDto> getTrades(
            String searchType,
            String keyword,
            LocalDate startDate,
            LocalDate endDate,
            int offset,
            int pageSize
    ) {
        return mypageTradesDao.searchTrades(
               searchType, keyword, startDate, endDate, offset, pageSize
        );
    }
}

