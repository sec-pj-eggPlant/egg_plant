package com.took.egg_plant_project.mypage.dto;

import com.took.egg_plant_project.entity.Post;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class MypagePostDto {
    private Integer postId;                     // POST.POSTID (게시글ID)
    private String title;                       // POST.TITLE (제목)
    private String location;                    // POST.LOCATION (위치)
    private String status;                      // POST.STATUS (게시글 상태)
    private String content;
    private Integer price;
    private Integer area;
    private LocalDate startDate;
    private LocalDate endDate;

    public MypagePostDto(Integer postId, String title, String location, String status,
                         String content, Integer price, Integer area,
                         LocalDate startDate, LocalDate endDate) {
        this.postId = postId;
        this.title = title;
        this.location = location;
        this.status = status;
        this.content = content;
        this.price = price;
        this.area = area;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public MypagePostDto(Integer postId, String title, String location, String status
    ) {
        this.postId = postId;
        this.title = title;
        this.location = location;
        this.status = status;
    }

    public Integer getId() {
        return postId;
    }

}
