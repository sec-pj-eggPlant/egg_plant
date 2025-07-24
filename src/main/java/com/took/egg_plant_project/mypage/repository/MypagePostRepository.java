package com.took.egg_plant_project.mypage.repository;

import com.took.egg_plant_project.entity.Post;
import com.took.egg_plant_project.mypage.dto.MypagePostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MypagePostRepository extends JpaRepository<Post, Integer> {
    @Query(value = """
            SELECT new com.took.egg_plant_project.mypage.dto.MypagePostDto(
                        p.id, p.title, p.location, p.status, p.content, p.price, p.area, p.startDate, p.endDate)
                        FROM Post p WHERE p.id = :postId
                                                """)
    MypagePostDto findPostDetailById(@Param("postId") Integer postId);

    @Query(value = """
            SELECT new com.took.egg_plant_project.mypage.dto.MypagePostDto(
                        p.id, p.title, p.location, p.status
                        )
                        FROM Post p
                        WHERE p.writer.id = :memberId
                        ORDER BY p.createdAt DESC
            """)
    List<MypagePostDto> findMyPostsByMemberId(@Param("memberId") Integer memberId);

    @Query(value = """
    SELECT new com.took.egg_plant_project.mypage.dto.MypagePostDto(
        p.id, p.title, p.location, p.status
    )
    FROM Post p
    WHERE p.writer.id = :memberId
    ORDER BY p.createdAt DESC
""")
    Page<MypagePostDto> findMyPostPageByMemberId(@Param("memberId") Integer memberId, Pageable pageable);
}

