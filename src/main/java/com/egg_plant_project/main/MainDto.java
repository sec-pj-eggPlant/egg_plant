package com.egg_plant_project.main;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MainDto {
    private String title;
    private String content;
    private Integer price;
    private Integer area;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
}
