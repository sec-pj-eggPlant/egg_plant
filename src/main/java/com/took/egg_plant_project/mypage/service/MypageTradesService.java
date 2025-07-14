package com.took.egg_plant_project.mypage.service;

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
    private final MypageTradesRepository mypageTradesRepository;

    public List<MypageTradesDto> searchTrades(String status, LocalDateTime startDate, LocalDateTime endDate, int page, int pageSize) {
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;
        List<Object[]> results = mypageTradesRepository.searchTrades(status, startDate, endDate, startRow, endRow);

        return results.stream().map(obj -> MypageTradesDto.builder()
                .tradeId(((Number) obj[0]).intValue())
                .status((String) obj[1])
                .createDate(obj[2] != null ? ((Timestamp) obj[2]).toLocalDateTime() : null)
                .renterName((String) obj[3])
                .ownerName((String) obj[4])
                .postTitle((String) obj[5])
                .location((String) obj[6])
                .price(obj[7] != null ? ((Number) obj[7]).intValue() : 0)
                .startDate(obj[8] != null ? ((Timestamp) obj[8]).toLocalDateTime() : null)
                .endDate(obj[9] != null ? ((Timestamp) obj[9]).toLocalDateTime() : null)
                .build()
        ).collect(Collectors.toList());
    }

}

