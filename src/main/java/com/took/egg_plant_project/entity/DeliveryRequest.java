package com.took.egg_plant_project.entity;

import com.took.egg_plant_project.constant.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_request")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DeliveryRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 오라클 트리거로 자동 증가
    private Long id;

    @Column(name = "tracking_number", nullable = false, unique = true, length = 100)
    private String trackingNumber;

    @Column(name = "receiver_address", nullable = false, length = 255)
    private String receiverAddress;

    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    @Column(name = "clearance_code", nullable = false, length = 50)
    private String clearanceCode;

    @Column(name = "locker_code", nullable = false, unique = true, length = 50)
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @PrePersist
    protected void onPrePersist() {
        this.requestedAt = LocalDateTime.now();
    }

    public static DeliveryRequest create(String trackingNumber, String address, String postalCode,
                                         String clearanceCode, String lockerCode,
                                         String itemName, Integer itemPrice,
                                         Member member) {
        return DeliveryRequest.builder()
                .trackingNumber(trackingNumber)
                .receiverAddress(address)
                .postalCode(postalCode)
                .clearanceCode(clearanceCode)
                .lockerCode(lockerCode)
                .itemName(itemName)
                .itemPrice(itemPrice)
                .member(member)
                .build();
    }

    public void updateStatus(DeliveryStatus newStatus) {
        this.status = newStatus;
    }
}
