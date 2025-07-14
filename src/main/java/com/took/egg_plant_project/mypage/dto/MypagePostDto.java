package com.took.egg_plant_project.mypage.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypagePostDto {
    private int id;                             // POST.POSTID (게시글ID)
    private String title;                       // POST.TITLE (제목)
    private String location;                    // POST.LOCATION (위치)
    private int price;                          // POST.PRICE (금액)
    private int area;                           // POST.AREA (면적)
    private LocalDate startDate;                // POST.STARTDATE (이용 시작일)
    private LocalDate endDate;                  // POST.ENDDATE (이용 종료일)
    private String status;                      // POST.STATUS (게시글 상태)
}
