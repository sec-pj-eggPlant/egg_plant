package com.took.egg_plant_project.warehouse;

import com.took.egg_plant_project.warehouse.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/tab-content-json")
    @ResponseBody
    public List<BoxDto> getTabContentJson(
            @RequestParam String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return warehouseService.getBoxesBySectorWithPeriod(type, startDate, endDate);
    }

    @PostMapping("/payment")
    public String paymentPage(
            @RequestParam("boxIds") List<Integer> boxIds,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            Model model
    ) {
        PaymentPageDto pageDto = warehouseService.getPaymentPageInfo(boxIds, startDate, endDate);

        // 여기서 boxNumber 문자열 만들어서 모델에 넣어도 되고,
        // 아니면 getPaymentPageInfo 안에서 Dto에 추가해도 됨.

        model.addAttribute("page", pageDto);

        return "warehouse/warehouse-payment";
    }


    @PostMapping("/apply-final")
    public String applyFinal(
            @RequestParam("boxIds") List<Integer> boxIds,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("paymentType") String paymentType,
            Model model
    ) {
        warehouseService.applyBoxesFinal(boxIds, startDate, endDate, paymentType);
        model.addAttribute("result", "신청 완료");
        return "warehouse/complete";
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
