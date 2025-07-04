package com.took.egg_plant_project.warehouse;

import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.entity.Warehouse;
import com.took.egg_plant_project.entity.WarehouseUse;
import com.took.egg_plant_project.member.MemberRepository;
import com.took.egg_plant_project.warehouse.dto.ApplyRequest;
import com.took.egg_plant_project.warehouse.dto.WarehouseDto;
import com.took.egg_plant_project.warehouse.dto.WarehouseUseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseUseRepository useRepository;
    private final MemberRepository memberRepository;
    private final WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true)
    public List<WarehouseUseDto> getBoxesBySector(String sector) {
        return useRepository.findByWarehouse_Sector(sector).stream()
                .map(WarehouseUseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<WarehouseUseDto> applyForBoxes(ApplyRequest req) {

        Member member = memberRepository.findById(1).orElseThrow(); // 실제 로그인 유저 ID 연계 필요

        for (Integer useId : req.getBoxes()) {
            WarehouseUse use = useRepository.findById(useId).orElseThrow();
            use.rent(LocalDate.now(), LocalDate.now().plusDays(Integer.parseInt(req.getPeriod())));
        }
        return useRepository.findByIdIn(req.getBoxes()).stream()
                .map(WarehouseUseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public WarehouseDto getPricingBySector(String sector) {
        // 1. List로 반환받고 비어 있는지 확인
        List<Warehouse> warehouses = warehouseRepository.findBySector(sector);
        if (warehouses.isEmpty()) {
            throw new IllegalArgumentException("Invalid sector");
        }

        // 2. 첫 번째 Warehouse를 선택 (필요에 따라 다른 처리 가능)
        Warehouse warehouse = warehouses.getFirst();

        return WarehouseDto.fromEntity(warehouse);  // 가격과 면적을 반환
    }
}
