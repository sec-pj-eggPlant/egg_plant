package com.took.egg_plant_project.mypage.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageTradesDto {
    private int tradeId;                // TRADE.TRADEID(거래ID)
    private String status;              // TRADE.STATUS(거래상태)
    private LocalDateTime createDate;  // TRADE.CREATEDATE (요청일)
    private String renterName;          // MEMBER.USERNAME (임차인)
    private String ownerName;           // MEMBER.USERNAME (임대인)
    private String postTitle;           // POST.TITLE(게시글/창고명)
    private String location;            // POST.LOCATION(위치)
    private int price;                  // POST.PRICE(금액)
//    private LocalDateTime startDate;    // WAREHOUSE_USE.STARTDATE(이용 시작일)
//    private LocalDateTime endDate;      // WAREHOUSE_USE.ENDDATE(이용 종료일)
}
