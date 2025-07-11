package com.took.egg_plant_project.warehouse.dto;

import com.took.egg_plant_project.entity.Box;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PaymentPageDto {

    private List<Box> boxes;
    private String startDate;
    private String endDate;
    private int totalArea;
    private int totalPrice;
    private String boxNumbersStr; // 박스번호 콤마 문자열

}
