package com.took.egg_plant_project.admin.service;

import com.took.egg_plant_project.admin.repository.AdminWarehouseUseRepository;
import com.took.egg_plant_project.entity.WarehouseUse;
import com.took.egg_plant_project.warehouse.WarehouseUseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminWarehouseUseService {
    private final AdminWarehouseUseRepository adminWarehouseUseRepository;

    public Page<WarehouseUse> findUsesByBoxId(Long boxId, String keyword, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return adminWarehouseUseRepository.findByBoxWithFilters(boxId, keyword, startDate, endDate, pageable);
    }

}
