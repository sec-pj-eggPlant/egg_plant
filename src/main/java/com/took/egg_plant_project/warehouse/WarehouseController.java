package com.took.egg_plant_project.warehouse;

import com.took.egg_plant_project.warehouse.WarehouseService;
import com.took.egg_plant_project.warehouse.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/warehouse")
    public String warehouseMain() {
        return "warehouse/warehouse";
    }

    @GetMapping("/tab-content")
    public String getTabContentView(@RequestParam String type, Model model) {
        model.addAttribute("boxes", warehouseService.getBoxesBySector(type));
        return switch (type) {
            case "A" -> "warehouse/tab-a :: content";
            case "B" -> "warehouse/tab-b :: content";
            case "C" -> "warehouse/tab-c :: content";
            default -> "warehouse/tab-empty :: content";
        };
    }

    @GetMapping("/tab-content-json")
    @ResponseBody
    public List<BoxDto> getTabContentJson(
            @RequestParam String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return warehouseService.getBoxesBySectorWithPeriod(type, startDate, endDate);
    }

    @PostMapping("/apply-boxes")
    @ResponseBody
    public List<BoxDto> applyBoxes(@RequestBody ApplyRequest req) {
        return warehouseService.applyForBoxes(req);
    }

    @GetMapping("/get-sector-pricing")
    @ResponseBody
    public BoxPricingDto getSectorPricing(@RequestParam String sector) {
        return warehouseService.getBoxPricingBySector(sector);
    }
}
