package com.took.egg_plant_project.BDJ.controller;

import com.took.egg_plant_project.BDJ.DeliveryRequestService;
import com.took.egg_plant_project.BDJ.ForwardingParcelService;
import com.took.egg_plant_project.BDJ.dto.DeliveryRequestDto;
import com.took.egg_plant_project.BDJ.dto.ForwardingParcelDto;
import com.took.egg_plant_project.BDJ.dto.ForwardingParcelDto;
import com.took.egg_plant_project.BDJ.ForwardingParcelService;
import com.took.egg_plant_project.constant.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/forwarding")
@RequiredArgsConstructor
public class ForwardingAdminController {

    private final DeliveryRequestService deliveryRequestService;
    private final ForwardingParcelService forwardingParcelService;

    // 입고된 상품 리스트
    @GetMapping("/inbound")
    public String inboundList(Model m) {
        m.addAttribute("parcels", forwardingParcelService.getAllParcels());
        return "admin/forwarding_list";
    }

    @PostMapping("/admin/forwarding/inbound")
    public String createForwardingParcel(@RequestParam Integer deliveryRequestId,
                                         @RequestParam String zone) {
        forwardingParcelService.createFromRequest(deliveryRequestId, zone);
        return "redirect:/admin/forwarding/inbound";
    }

    @GetMapping("/list")
    public String requestList(Model model) {
        List<DeliveryRequestDto> requests = deliveryRequestService.getAllRequests();
        Set<Integer> inboundedIds = forwardingParcelService.getAllParcels()
                .stream()
                .map(p -> p.getDeliveryRequest().getId())
                .collect(Collectors.toSet());
        model.addAttribute("requests", requests);
        model.addAttribute("inboundedIds", inboundedIds);
        return "admin/request_list";
    }

    @GetMapping("/request_detail/{id}")
    public String requestDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("requestDto", deliveryRequestService.getDtoById(id));
        return "admin/request_detail";
    }

    @PostMapping("/request_detail/{id}")
    public String inbound(@PathVariable Integer id, @RequestParam String zone) {
        forwardingParcelService.createFromRequest(id, zone);
        return "redirect:/admin/forwarding/list";
    }
}

