package com.took.egg_plant_project.BDJ.dto;

import com.took.egg_plant_project.constant.DeliveryStatus;
import com.took.egg_plant_project.entity.DeliveryRequest;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForwardingParcelDto {
    private Integer id;
    private String memberName;
    private String memberTel;
    private String trackingNumber;
    private String domesticTrackingNumber;
    private DeliveryStatus status;
    private LocalDate receivedDate;
    private LocalDate shippedDate;
    private LocalDate deliveredDate;
    private String zone;
    private DeliveryRequest deliveryRequest;

}

