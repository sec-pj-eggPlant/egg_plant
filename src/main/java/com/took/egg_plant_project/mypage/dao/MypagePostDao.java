package com.took.egg_plant_project.mypage.dao;

import com.took.egg_plant_project.entity.Post;
import com.took.egg_plant_project.mypage.dto.MypagePostDto;
import com.took.egg_plant_project.mypage.repository.MypagePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MypagePostDao {
    private final MypagePostRepository mypagePostRepository;

    public List<MypagePostDto> findMyPosts(Integer memberId) {
        return mypagePostRepository.findMyPostsByMemberId(memberId);
    }

    public MypagePostDto findPostDetail(Integer postId) {
        return mypagePostRepository.findPostDetailById(postId);
    }
}
