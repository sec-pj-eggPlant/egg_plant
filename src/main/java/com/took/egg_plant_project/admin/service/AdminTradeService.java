package com.took.egg_plant_project.admin.service;

import com.took.egg_plant_project.admin.repository.AdminTradeRepository;
import com.took.egg_plant_project.entity.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminTradeService {

    private final AdminTradeRepository adminTradeRepository;

    public Page<Trade> searchByCondition(String category, String keyword, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime end = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        String status = null;
        String renterId = null;
        String ownerId = null;

        if (keyword != null && !keyword.isBlank()) {
            switch (category) {
                case "renter" -> renterId = keyword;
                case "owner" -> ownerId = keyword;
                case "status" -> status = keyword;
            }
        }

        return adminTradeRepository.findFilteredTrades(status, renterId, ownerId, start, end, pageable);
    }

    public Map<Integer, Long> getMonthlyDoneTradeCounts() {
        List<Object[]> results = adminTradeRepository.countDoneTradesByMonth();
        Map<Integer, Long> monthlyDoneCount = new HashMap<>();

        for (Object[] row : results) {
            Integer month = ((Number) row[0]).intValue(); // 1~12
            Long count = ((Number) row[1]).longValue();
            monthlyDoneCount.put(month, count);
        }

        // 1~12월까지 누락 없이 0으로 채우기
        for (int i = 1; i <= 12; i++) {
            monthlyDoneCount.putIfAbsent(i, 0L);
        }

        return monthlyDoneCount;
    }


}
