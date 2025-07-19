package com.took.egg_plant_project.mypage.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class MypageTradesDto {
    private int tradeId;
    private String status;
    private Timestamp createDate;
    private String renterName;
    private String ownerName;
    private String postTitle;
    private String location;
    private int price;

    public MypageTradesDto(int tradeId, String status, Timestamp createDate,
                           String renterName, String ownerName, String postTitle,
                           String location, int price) {
        this.tradeId = tradeId;
        this.status = status;
        this.createDate = createDate;
        this.renterName = renterName;
        this.ownerName = ownerName;
        this.postTitle = postTitle;
        this.location = location;
        this.price = price;
    }

}
