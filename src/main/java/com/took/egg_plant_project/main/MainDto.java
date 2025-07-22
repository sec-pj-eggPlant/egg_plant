package com.took.egg_plant_project.main;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MainDto {
    private Integer id;
    private String title;
    private String content;
    private Integer price;
    private Integer area;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String imagePath;
    private String writerRole;
    private Double latitude;
    private Double longitude;
    private Integer writerId;
}
