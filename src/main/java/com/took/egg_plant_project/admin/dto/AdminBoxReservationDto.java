package com.took.egg_plant_project.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AdminBoxReservationDto {
    private String boxNumber;
    private String sector;
    private String userID;
    private LocalDate startDate;
    private LocalDate endDate;
}