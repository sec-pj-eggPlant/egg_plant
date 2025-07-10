package com.took.egg_plant_project.warehouse;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoxInfo {

    private String boxNumber;
    private int area;
    private int pricePerDay;

}
