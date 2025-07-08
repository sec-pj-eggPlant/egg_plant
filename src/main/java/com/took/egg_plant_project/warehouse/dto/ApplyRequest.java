package com.took.egg_plant_project.warehouse.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
// ApplyRequest.java
public class ApplyRequest {
    private List<Integer> boxes;    // 이제 warehouseUseID 가 아니라 Warehouse.id 들을 보냄
    private String startDate;
    private String endDate;
    // getters & setters
}



