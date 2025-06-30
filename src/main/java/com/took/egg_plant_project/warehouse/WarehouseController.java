package com.took.egg_plant_project.warehouse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {

    @GetMapping("/warehouse-main")
    public String warehouseMain(){
        return "warehouse/warehouse-main";
    }
}
