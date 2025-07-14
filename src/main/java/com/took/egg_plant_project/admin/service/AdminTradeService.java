package com.took.egg_plant_project.admin.service;

import com.took.egg_plant_project.admin.repository.AdminTradeRepository;
import com.took.egg_plant_project.entity.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminTradeService {

    private final AdminTradeRepository adminTradeRepository;

    public List<Trade> searchByCondition(String category, String keyword, LocalDate startDate, LocalDate endDate) {
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

        return adminTradeRepository.findFilteredTrades(status, renterId, ownerId, start, end);
    }


}
