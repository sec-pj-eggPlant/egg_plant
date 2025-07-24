package com.took.egg_plant_project.admin.controller;

import com.took.egg_plant_project.admin.dto.AdminBoxReservationDto;
import com.took.egg_plant_project.admin.service.AdminWarehouseService;
import com.took.egg_plant_project.admin.service.AdminWarehouseUseService;
import com.took.egg_plant_project.entity.WarehouseUse;
import com.took.egg_plant_project.warehouse.WarehouseService;
import com.took.egg_plant_project.warehouse.dto.BoxDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminWarehouseController {
    private final AdminWarehouseService adminWarehouseService;
    private final AdminWarehouseUseService adminWarehouseUseService;

    @GetMapping("/admin/warehouse")
    public String adminWarehouseView(@RequestParam(required = false) String sector, Model model) {
        List<BoxDto> boxList = adminWarehouseService.getAllBoxes(sector);
        model.addAttribute("boxList", boxList);
        model.addAttribute("sector", sector);
        return "admin/warehouse";
    }

    @GetMapping("/admin/warehouse/detail/{boxId}")
    public String showBoxDetail(@PathVariable("boxId") Long boxId,
                                @RequestParam(required = false) String keyword,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                @RequestParam(defaultValue = "0") int page,
                                Model model) {
        if (keyword != null && keyword.isBlank()) {
            keyword = null;
        }

        Pageable pageable = PageRequest.of(page, 15); // 페이지 번호, 페이지 크기
        Page<WarehouseUse> pageResult = adminWarehouseUseService.findUsesByBoxId(boxId, keyword, startDate, endDate, pageable);

        model.addAttribute("usePage", pageResult);
        model.addAttribute("boxId", boxId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "admin/warehouse/detail";
    }

}
