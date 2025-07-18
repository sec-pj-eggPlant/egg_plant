package com.took.egg_plant_project.BDJ;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryRequestDto {

    private String trackingNumber;
    private String address;         // 기본 주소
    private String addressDetail;   // 상세 주소
    private String postalCode;
    private String clearanceCode;   // 개인통관번호
    private String itemName;
    private Integer itemPrice;
}



