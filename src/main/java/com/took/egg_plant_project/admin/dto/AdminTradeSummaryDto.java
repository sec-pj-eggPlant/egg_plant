package com.took.egg_plant_project.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminTradeSummaryDto {

    private Integer id;
    private Integer postId;
    private String renterId;
    private String ownerId;
    private String status;
    private LocalDateTime createdAt;
}
