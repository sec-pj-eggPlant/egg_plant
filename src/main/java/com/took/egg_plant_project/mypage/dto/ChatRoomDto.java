package com.took.egg_plant_project.mypage.dto;

import lombok.Getter;

@Getter
public class ChatRoomDto {
    private int chatRoomId;
    private int postId;
    private String title;
    private int renterId;
    private String renterName;
    private int ownerId;
    private String ownerName;
}
