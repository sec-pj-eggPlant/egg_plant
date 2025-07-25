package com.took.egg_plant_project.entity;

import com.took.egg_plant_project.constant.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_request")
@SequenceGenerator(
        name = "delivery_request_seq_gen",
        sequenceName = "DELIVERY_REQUEST_SEQ",
        allocationSize = 1
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DeliveryRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_request_seq_gen")
    private Integer id;

    @Column(name = "tracking_number", nullable = false, unique = true, length = 100)
    private String trackingNumber;

    @Column(name = "receiver_address", nullable = false, length = 255)
    private String receiverAddress;

    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    @Column(name = "clearance_code", nullable = false, length = 50)
    private String clearanceCode;

    @Column(name = "locker_code", nullable = false, length = 50)
    private String lockerCode;

    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @Column(name = "item_price", nullable = false)
    private Integer itemPrice;

    @Column(name = "requested_at", nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static DeliveryRequest create(String trackingNumber,
                                         String receiverAddress,
                                         String postalCode,
                                         String clearanceCode,
                                         String lockerCode,
                                         String itemName,
                                         Integer itemPrice,
                                         Member member) {
        return DeliveryRequest.builder()
                .trackingNumber(trackingNumber)
                .receiverAddress(receiverAddress)
                .postalCode(postalCode)
                .clearanceCode(clearanceCode)
                .lockerCode(lockerCode)
                .itemName(itemName)
                .itemPrice(itemPrice)
                .member(member)
                .requestedAt(LocalDateTime.now())  // ★ 이 한 줄 추가
                .build();
    }


}
