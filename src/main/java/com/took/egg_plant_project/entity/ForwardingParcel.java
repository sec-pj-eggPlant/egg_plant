package com.took.egg_plant_project.entity;

import com.took.egg_plant_project.constant.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "forwarding_parcel")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ForwardingParcel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forwarding_parcel_seq")
    @SequenceGenerator(name = "forwarding_parcel_seq", sequenceName = "FORWARDING_PARCEL_SEQ", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_request_id", nullable = false)
    private DeliveryRequest deliveryRequest;

    @Column(name = "zone", length = 10, nullable = false)
    private String zone;

    @Column(name = "tracking_number", nullable = false)
    private String trackingNumber;

    @Column(name = "received_date", nullable = false)
    private LocalDate receivedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @Column(name = "shipped_date", nullable = false)
    private LocalDate shippedDate;

    @Column(name = "delivered_date", nullable = false)
    private LocalDate deliveredDate;

    public static ForwardingParcel create(DeliveryRequest deliveryRequest, String zone) {
        return ForwardingParcel.builder()
                .deliveryRequest(deliveryRequest)
                .member(deliveryRequest.getMember())
                .trackingNumber(deliveryRequest.getTrackingNumber())
                .zone(zone)
                .status(DeliveryStatus.RECEIVED)
                .receivedDate(LocalDate.now())
                .build();
    }
}
