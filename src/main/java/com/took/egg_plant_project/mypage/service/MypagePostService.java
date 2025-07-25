package com.took.egg_plant_project.mypage.service;

import com.took.egg_plant_project.mypage.dao.MypagePostDao;
import com.took.egg_plant_project.mypage.dto.MypagePostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypagePostService {

    private final MypagePostDao mypagePostDao;

    // 1. 내가 쓴 글 목록
    public List<MypagePostDto> getMyPosts(Integer memberId) {
        List<MypagePostDto> results = mypagePostDao.findMyPosts(memberId);
        return results;
    }

    // 2. 게시글 상세
    public MypagePostDto getPostById(Integer postId) {
        MypagePostDto row = mypagePostDao.findPostDetail(postId);
        if (row == null) {
            return null;
        }

        return row;
    }

    // 유틸: 정수형 안전 변환
    private Integer getInt(Object obj) {
        return obj != null ? ((Number) obj).intValue() : null;
    }

    // 유틸: Timestamp → LocalDateTime 안전 변환
    private java.time.LocalDateTime getTimestamp(Object obj) {
        return obj != null ? ((Timestamp) obj).toLocalDateTime() : null;
    }

    public Page<MypagePostDto> getMyPosts(Integer memberId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return mypagePostDao.findMyPosts(memberId, pageable);
    }
}