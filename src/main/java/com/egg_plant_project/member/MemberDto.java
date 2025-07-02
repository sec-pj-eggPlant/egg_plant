package com.egg_plant_project.member;

import com.egg_plant_project.constant.Role;
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
}
