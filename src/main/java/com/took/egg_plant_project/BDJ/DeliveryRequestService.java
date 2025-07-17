package com.took.egg_plant_project.BDJ;

import com.took.egg_plant_project.entity.DeliveryRequest;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryRequestService {

    private final MemberRepository memberRepository;
    private final DeliveryRequestRepository deliveryRequestRepository;

    public DeliveryRequestDto prepareRequestForm(String userId) {
        Member member = memberRepository.findByUserID(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        return new DeliveryRequestDto(); // 미리 정보 넣고 싶으면 여기서 추가
    }

    public void createDeliveryRequest(DeliveryRequestDto dto, String userId) {
        Member member = memberRepository.findByUserID(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        String fullAddress = dto.getAddress() + " " + dto.getAddressDetail();

        DeliveryRequest request = DeliveryRequest.builder()
                .trackingNumber(dto.getTrackingNumber())
                .receiverAddress(fullAddress)
                .postalCode(dto.getPostalCode())
                .clearanceCode(dto.getClearanceCode())
                .lockerCode(member.getLockerCode()) // 여기!
                .itemName(dto.getItemName())
                .itemPrice(dto.getItemPrice())
                .member(member)
                .build();


        deliveryRequestRepository.save(request);
    }
}
