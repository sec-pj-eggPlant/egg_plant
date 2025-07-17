package com.took.egg_plant_project.BDJ;

import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/BDJ")
@Controller
@Slf4j
@RequiredArgsConstructor
public class BDJController {


    private final DeliveryRequestService deliveryRequestService;
    private final MemberService memberService;

    @GetMapping("/request")
    public String showRequestForm(Model model, Principal principal) {
        DeliveryRequestDto dto = deliveryRequestService.prepareRequestForm(principal.getName());
        Member member = memberService.getByUserID(principal.getName());
        model.addAttribute("requestDto", dto);
        model.addAttribute("member", member);
        return "BDJ/delivery_request";
    }


    @PostMapping("/request")
    public String submitRequest(@ModelAttribute DeliveryRequestDto dto, Principal principal) {
        deliveryRequestService.createDeliveryRequest(dto, principal.getName());
        return "redirect:/BDJ/complete";
    }

    @GetMapping("/complete")
    public String requestComplete() {
        return "BDJ/complete";
    }
}