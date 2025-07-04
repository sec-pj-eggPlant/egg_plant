package com.took.egg_plant_project.warehouse.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyRequest {
    private List<Integer> boxes;
    private String period;
}


