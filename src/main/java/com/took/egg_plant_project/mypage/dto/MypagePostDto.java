package com.took.egg_plant_project.mypage.dto;

import com.took.egg_plant_project.entity.Post;
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
    private String status;                      // POST.STATUS (게시글 상태)

    public static MypagePostDto fromEntity(Post post) {
        return MypagePostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .location(post.getLocation())
                .status(post.getStatus())
                .build();
    }
}
