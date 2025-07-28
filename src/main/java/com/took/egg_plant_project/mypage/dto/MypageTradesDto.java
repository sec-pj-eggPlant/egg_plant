package com.took.egg_plant_project.mypage.dto;

import com.took.egg_plant_project.constant.TradeStatus;
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
    private TradeStatus status;
    private Timestamp createDate;
    private String renterName;
    private String ownerName;
    private String postTitle;
    private String location;
    private int price;

    public MypageTradesDto(int tradeId, TradeStatus status, Timestamp createDate,
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

//    public TradeStatus getStatus() {
//        return status;
//    }

    public String getStatusKorean() {

            if (status == null) return "알 수 없음";

        return switch (status) {
            case IN_PROGRESS -> "거래중";
            case ACTIVE -> "거래가능";
            case DONE -> "거래완료";
            default -> "알 수 없음";
        };
    }

//    public void setStatus(TradeStatus status) {
//        this.status = status;
//    }

}
