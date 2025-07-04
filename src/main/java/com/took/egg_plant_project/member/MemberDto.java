package com.took.egg_plant_project.member;

import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private Integer id;
    private String userID;
    private String userPW;
    private String nickName;
    private String userName;
    private String userEmail;
    private String tel;
    private LocalDateTime createdAt;
    private Role role;

    public Member toMember() {
        return Member.builder()
                .userID(this.userID)
                .userPW(this.userPW)
                .nickName(this.nickName)
                .userName(this.userName)
                .userEmail(this.userEmail)
                .tel(this.tel)
                .role(this.role)
                .build();
    }
}
