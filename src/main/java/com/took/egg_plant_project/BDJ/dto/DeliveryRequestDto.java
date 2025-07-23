package com.took.egg_plant_project.BDJ.dto;

import com.took.egg_plant_project.constant.DeliveryStatus;
import com.took.egg_plant_project.entity.DeliveryRequest;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDto {

    private Integer id;
    private String receiverAddress;
    private LocalDate receivedDate;
    private String postalCode;
    private String clearanceCode;
    private String lockerCode;
    private String itemName;
    private Integer itemPrice;
    private String trackingNumber;
    private DeliveryStatus status;
    private String domesticTrackingNumber;
    private LocalDateTime requestedAt;
    private String memberName;
    private String memberTel;

}

