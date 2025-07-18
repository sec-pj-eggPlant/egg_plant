package com.took.egg_plant_project.mypage.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageChatDto {
    private String userNickname;
    private String title;
    private String location;
    private String lastChatDate;
}
