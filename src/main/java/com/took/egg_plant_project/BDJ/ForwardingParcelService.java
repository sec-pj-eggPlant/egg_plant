package com.took.egg_plant_project.BDJ;

import com.took.egg_plant_project.constant.DeliveryStatus;
import com.took.egg_plant_project.BDJ.dto.ForwardingParcelDto;
import com.took.egg_plant_project.entity.DeliveryRequest;
import com.took.egg_plant_project.entity.ForwardingParcel;
import com.took.egg_plant_project.BDJ.ForwardingParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForwardingParcelService {

    private final ForwardingParcelRepository forwardingParcelRepository;
    private final DeliveryRequestRepository deliveryRequestRepository;

    @Transactional(readOnly = true)
    public ForwardingParcelDto getDtoById(Integer id) {
        ForwardingParcel parcel = forwardingParcelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 입고를 찾을 수 없습니다."));
        return toDto(parcel);
    }

    @Transactional(readOnly = true)
    public List<ForwardingParcelDto> getAllParcels() {
        return forwardingParcelRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    private ForwardingParcelDto toDto(ForwardingParcel parcel) {
        return ForwardingParcelDto.builder()
                .id(parcel.getId())
                .memberName(parcel.getDeliveryRequest().getMember().getUserName())
                .memberTel(parcel.getDeliveryRequest().getMember().getTel())
                .trackingNumber(parcel.getTrackingNumber())
                .status(parcel.getStatus())
                .receivedDate(parcel.getReceivedDate())
                .shippedDate(parcel.getShippedDate())
                .deliveredDate(parcel.getDeliveredDate())
                .deliveryRequest(parcel.getDeliveryRequest())  // ★ 이 한줄 추가!
                .build();
    }

    @Transactional
    public void createFromRequest(Integer deliveryRequestId, String zone) {

        DeliveryRequest deliveryRequest = deliveryRequestRepository.findById(deliveryRequestId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신청이 존재하지 않습니다."));

        ForwardingParcel parcel = ForwardingParcel.create(deliveryRequest, zone);

        forwardingParcelRepository.save(parcel);
    }
}
