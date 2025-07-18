package com.took.egg_plant_project.admin.service;

import com.took.egg_plant_project.admin.dto.AdminBoxReservationDto;
import com.took.egg_plant_project.admin.repository.AdminBoxRepository;
import com.took.egg_plant_project.admin.repository.AdminWarehouseUseRepository;
import com.took.egg_plant_project.entity.Box;
import com.took.egg_plant_project.entity.WarehouseUse;
import com.took.egg_plant_project.warehouse.dto.BoxDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminWarehouseService {

    private final AdminBoxRepository adminBoxRepository;
    private final AdminWarehouseUseRepository adminWarehouseUseRepository;

    public AdminBoxReservationDto getReservationDetail(Long boxId) {
        WarehouseUse use = adminWarehouseUseRepository.findLatestByBoxId(boxId)
                .orElseThrow(() -> new NoSuchElementException("예약 정보 없음"));

        return new AdminBoxReservationDto(
                use.getBox().getBoxNumber(),
                use.getWarehouse().getSector(),
                use.getUser().getUserID(),
                use.getStartDate(),
                use.getEndDate()
        );
    }

    public List<BoxDto> getAllBoxes(String sector) {
        List<Box> boxes;
        if (sector != null && !sector.isEmpty()) {
            boxes = adminBoxRepository.findByWarehouse_Sector(sector);
        } else {
            boxes = adminBoxRepository.findAllWithWarehouse();
        }

        LocalDate today = LocalDate.now();

        return boxes.stream()
                .map(box -> {
                    boolean rented = adminWarehouseUseRepository.isRented(box, today);
                    String status = rented ? "RENTED" : "AVAILABLE";

                    return new BoxDto(
                            box.getId(),
                            box.getWarehouse().getSector(),
                            box.getBoxNumber(),
                            status
                    );
                })
                .sorted(
                        java.util.Comparator
                                .comparing(BoxDto::getSector)
                                .thenComparing(dto -> {
                                    String digits = dto.getBoxNumber().replaceAll("\\D+", "");
                                    return digits.isEmpty() ? 0 : Integer.parseInt(digits);
                                })
                )
                .toList();
    }

    public Map<String, Integer> getTodayWarehouseUsageRate() {
        LocalDate today = LocalDate.now();
        Map<String, Integer> usageRateMap = new HashMap<>();

        List<String> sectors = List.of("A", "B", "C");

        for (String sector : sectors) {
            int total = adminBoxRepository.countByWarehouse_Sector(sector);  // 전체 박스 수
            int inUse = adminWarehouseUseRepository.countBySectorAndDate(sector, today); // 오늘 임대 중인 박스 수

            int rate = total == 0 ? 0 : (int) Math.round((double) inUse / total * 100);
            usageRateMap.put(sector, rate);
        }

        return usageRateMap;
    }

}

