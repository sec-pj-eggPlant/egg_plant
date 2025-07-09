package com.took.egg_plant_project.mypage.repository;

import com.took.egg_plant_project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MypagePostRepository extends JpaRepository<Post, Integer> {
    @Query(value = """
            SELECT POSTID,TITLE,LOCATION,PRICE,CREATEDATE,STATUS       
            FROM POST
            WHERE WRITERID = :memberId
            ORDER BY CREATEDATE DESC
            """, nativeQuery = true)
    List<Object[]> findMyPostsByMemberId(@Param("memberId") Integer memberId);
}

