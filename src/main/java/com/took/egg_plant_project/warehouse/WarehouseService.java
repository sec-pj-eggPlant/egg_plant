package com.took.egg_plant_project.warehouse;

import com.took.egg_plant_project.communal.CustomUserDetails;
import com.took.egg_plant_project.entity.Box;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.entity.Warehouse;
import com.took.egg_plant_project.entity.WarehouseUse;
import com.took.egg_plant_project.member.MemberRepository;
import com.took.egg_plant_project.warehouse.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    // 가격/면적 조회
    @Transactional(readOnly = true)
    public BoxPricingDto getBoxPricingBySector(String sector) {
        Warehouse warehouse = warehouseRepository.findBySector(sector)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sector: " + sector));
        return new BoxPricingDto(warehouse.getPricePerDay(), warehouse.getArea());
    }

    @Transactional
    public List<BoxDto> applyForBoxes(ApplyRequest req) {

        // 현재 로그인된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer memberId = userDetails.getId(); // CustomUserDetails에 getId() 메서드가 있다고 가정

        Member member = memberRepository.findById(memberId)
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
                    w.getSector(),
                    box.getBoxNumber(),
                    "RENTED"
            ));
        }
        return result;
    }

    public PaymentPageDto getPaymentPageInfo(List<Integer> boxIds, String startDate, String endDate) {
        List<Box> boxes = boxRepository.findAllById(boxIds);
        int totalArea = 0;
        int totalPrice = 0;
        List<BoxInfo> boxInfoList = new ArrayList<>();
        for (Box box : boxes) {
            int area = box.getWarehouse().getArea();
            int pricePerDay = box.getWarehouse().getPricePerDay();
            totalArea += area;
            totalPrice += pricePerDay;
            boxInfoList.add(new BoxInfo(box.getBoxNumber(), area, pricePerDay));
        }
        // ★ 여기서 박스번호 문자열 생성
        String boxNumbersStr = boxes.stream()
                .map(Box::getBoxNumber)
                .collect(Collectors.joining(", "));

        PaymentPageDto dto = new PaymentPageDto();
        dto.setBoxes(boxes); // 또는 boxInfoList 등 필요한 걸로
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setTotalArea(totalArea);
        dto.setTotalPrice(totalPrice);
        dto.setBoxNumbersStr(boxNumbersStr); // ★ 추가

        return dto;
    }

    @Transactional
    public void applyBoxesFinal(List<Integer> boxIds, String startDate, String endDate, String paymentType) {

        // 현재 로그인된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer memberId = userDetails.getId(); // CustomUserDetails에 getId() 메서드가 있다고 가정

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("사용자 없음"));

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        for (Integer boxId : boxIds) {
            Box box = boxRepository.findById(boxId).orElseThrow();
            WarehouseUse use = WarehouseUse.create(box, member, start, end);
            useRepository.save(use);
        }
        // 결제방식(paymentType)은 기록만 필요하면 DB 기록 등 추가 가능
    }
}

