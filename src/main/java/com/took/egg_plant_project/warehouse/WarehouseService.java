package com.took.egg_plant_project.warehouse;

import com.took.egg_plant_project.entity.Box;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.entity.Warehouse;
import com.took.egg_plant_project.entity.WarehouseUse;
import com.took.egg_plant_project.member.MemberRepository;
import com.took.egg_plant_project.warehouse.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseUseRepository useRepository;
    private final BoxRepository boxRepository;
    private final MemberRepository memberRepository;
    private final WarehouseRepository warehouseRepository;

    // 기간별 박스 상태
    @Transactional(readOnly = true)
    public List<BoxDto> getBoxesBySectorWithPeriod(String sector, LocalDate startDate, LocalDate endDate) {
        return boxRepository.findByWarehouseSector(sector).stream()
                .map(b -> {
                    boolean rented = false;
                    if (startDate != null && endDate != null) {
                        rented = useRepository
                                .existsByBox_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                                        b.getId(), endDate, startDate);
                    }
                    // 박스 정보는 warehouse에서 가져옴
                    Warehouse w = b.getWarehouse();
                    return new BoxDto(
                            b.getId(),
                            w.getSector(),         // sector
                            b.getBoxNumber(),      // boxNumber
                            rented ? "RENTED" : "AVAILABLE"
                    );
                })
                .toList();
    }

    // 오늘 기준 박스 상태
    @Transactional(readOnly = true)
    public List<BoxDto> getBoxesBySector(String sector) {
        LocalDate today = LocalDate.now();
        return boxRepository.findByWarehouseSector(sector).stream()
                .map(b -> {
                    boolean rented = useRepository
                            .existsByBox_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                                    b.getId(), today, today);
                    Warehouse w = b.getWarehouse();
                    return new BoxDto(
                            b.getId(),
                            w.getSector(),        // sector
                            b.getBoxNumber(),
                            rented ? "RENTED" : "AVAILABLE"
                    );
                })
                .toList();
    }

    // 가격/면적 조회
    @Transactional(readOnly = true)
    public BoxPricingDto getBoxPricingBySector(String sector) {
        Warehouse warehouse = warehouseRepository.findBySector(sector)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sector: " + sector));
        return new BoxPricingDto(warehouse.getPricePerDay(), warehouse.getArea());
    }

    // 박스 대여 신청 (그대로, 단 box 필드 참조만 조심)
    @Transactional
    public List<BoxDto> applyForBoxes(ApplyRequest req) {
        Member member = memberRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("사용자 없음"));

        LocalDate start = LocalDate.parse(req.getStartDate());
        LocalDate end = LocalDate.parse(req.getEndDate());
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("시작일이 종료일보다 이후일 수 없습니다.");
        }

        List<BoxDto> result = new ArrayList<>();
        for (Integer boxId : req.getBoxes()) {
            Box box = boxRepository.findById(boxId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 박스: " + boxId));

            boolean overlap = useRepository
                    .existsByBox_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                            boxId, end, start);
            if (overlap) {
                throw new IllegalStateException("이미 대여 중인 기간입니다: 박스ID=" + boxId);
            }

            WarehouseUse use = WarehouseUse.create(box, member, start, end);
            useRepository.save(use);

            Warehouse w = box.getWarehouse();
            result.add(new BoxDto(
                    box.getId(),
                    w.getSector(),   // sector
                    box.getBoxNumber(),
                    "RENTED"
            ));
        }
        return result;
    }
}

