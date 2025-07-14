package com.took.egg_plant_project.mypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MypageUpdateDto {
    private String userName;
    private String nickName;
    private String tel;
    private String userID;
    private String userEmail;
    private String userPW;
    private String newPW;
    private String confirmPW;
}