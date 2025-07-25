package com.took.egg_plant_project.BDJ;

import com.took.egg_plant_project.BDJ.dto.DeliveryRequestDto;
import com.took.egg_plant_project.entity.DeliveryRequest;
import com.took.egg_plant_project.entity.Member;
import com.took.egg_plant_project.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryRequestService {

    private final MemberRepository memberRepository;
    private final DeliveryRequestRepository deliveryRequestRepository;

    private DeliveryRequestDto toDto(DeliveryRequest req) {
        return DeliveryRequestDto.builder()
                .id(req.getId())
                .trackingNumber(req.getTrackingNumber())
                .receiverAddress(req.getReceiverAddress())
                .postalCode(req.getPostalCode())
                .clearanceCode(req.getClearanceCode())
                .lockerCode(req.getLockerCode())
                .itemName(req.getItemName())
                .itemPrice(req.getItemPrice())
                .requestedAt(req.getRequestedAt())
                .memberName(req.getMember().getUserName())
                .memberTel(req.getMember().getTel())
                .build();
    }


    /** 사용자 신청 폼 초기화 */
    public DeliveryRequestDto prepareRequestForm(String userId) {
        // 필요시 회원 기본정보 미리 바인딩
        DeliveryRequestDto dto = new DeliveryRequestDto();
        Member member = memberRepository.findByUserID(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        dto.setLockerCode(member.getLockerCode());
        return dto;
    }

    /** 사용자 신청 저장 */
    @Transactional
    public void createDeliveryRequest(DeliveryRequestDto dto, String userId) {
        Member member = memberRepository.findByUserID(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        String lockerCode = "WHS-" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();

        DeliveryRequest request = DeliveryRequest.create(
                dto.getTrackingNumber(),
                dto.getReceiverAddress(),
                dto.getPostalCode(),
                dto.getClearanceCode(),
                lockerCode,
                dto.getItemName(),
                dto.getItemPrice(),
                member
        );
        deliveryRequestRepository.save(request);
    }

    /** 단일 DTO 조회 (입고 폼용) */
    @Transactional(readOnly = true)
    public DeliveryRequestDto getDtoById(Integer id) {
        DeliveryRequest req = deliveryRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청이 없습니다."));
        return toDto(req);
    }

    // DeliveryRequestService
    @Transactional(readOnly = true)
    public List<DeliveryRequestDto> getAllRequests() {
        return deliveryRequestRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
