package com.took.egg_plant_project.warehouse;

import com.took.egg_plant_project.warehouse.WarehouseService;
import com.took.egg_plant_project.warehouse.dto.ApplyRequest;
import com.took.egg_plant_project.warehouse.dto.WarehouseDto;
import com.took.egg_plant_project.warehouse.dto.WarehouseUseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

//    @GetMapping("/warehouse")
//    public String warehouse(Model model) {
//        return "warehouse/warehouse";
//    }

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
    public List<WarehouseUseDto> getTabContentJson(@RequestParam String type) {
        return warehouseService.getBoxesBySector(type);
    }

    // 신청 메서드
    @PostMapping("/apply-boxes")
    @ResponseBody
    public ResponseEntity<List<WarehouseUseDto>> applyBoxes(@RequestBody ApplyRequest req) {
        List<WarehouseUseDto> updatedBoxes = warehouseService.applyForBoxes(req);  // 서비스에서 처리 후 반환된 상태
        return ResponseEntity.ok(updatedBoxes);
    }

    @GetMapping("/get-sector-pricing")
    @ResponseBody
    public WarehouseDto getSectorPricing(@RequestParam String sector) {
        // DB에서 sector에 맞는 가격과 면적을 가져옴
        return warehouseService.getPricingBySector(sector);
    }
}
