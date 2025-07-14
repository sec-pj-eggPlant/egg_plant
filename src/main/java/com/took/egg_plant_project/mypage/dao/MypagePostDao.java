package com.took.egg_plant_project.mypage.dao;

import com.took.egg_plant_project.entity.Post;
import com.took.egg_plant_project.mypage.repository.MypagePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MypagePostDao {
    private final MypagePostRepository mypagePostRepository;

    public List<Object[]> findMyPosts(Integer memberId) {
        return mypagePostRepository.findMyPostsByMemberId(memberId);
    }
}
