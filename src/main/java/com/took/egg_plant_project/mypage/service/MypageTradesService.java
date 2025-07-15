package com.took.egg_plant_project.mypage.service;

import com.took.egg_plant_project.mypage.dao.MypageTradesDao;
import com.took.egg_plant_project.mypage.dto.MypageTradesDto;
import com.took.egg_plant_project.mypage.repository.MypageTradesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageTradesService {
    private final MypageTradesDao mypageTradesDao;


    public List<MypageTradesDto> getTrades(
            int userId,
            String status,
            String title,
            String renterName,
            String ownerName,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int pageSize
    ) {
        int offset = (page - 1) * pageSize;
        return mypageTradesDao.searchTrades(
                userId, status, title, renterName, ownerName, startDate, endDate,
                offset, pageSize
        );
    }
}

//    /**
//     * 거래내역 전체 건수(검색조건 반영)
//     */
//    public int countTrades(
//            int userId,
//            String searchType,
//            String keyword,
//            LocalDateTime startDate,
//            LocalDateTime endDate
//    ) {
//        return mypageTradesDao.countTrades(
//                userId, searchType, keyword, startDate, endDate
//        );
//    }
//}

