package com.took.egg_plant_project.mypage.service;

import com.took.egg_plant_project.mypage.dao.MypagePostDao;
import com.took.egg_plant_project.mypage.dto.MypagePostDto;
import lombok.RequiredArgsConstructor;
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
        List<Object[]> results = mypagePostDao.findMyPosts(memberId);
        return results.stream()
                .map(obj -> MypagePostDto.builder()
                        .id(((Number) obj[0]).intValue())
                        .title((String) obj[1])
                        .location((String) obj[2])
                        .status((String) obj[3])
                        .build())
                .collect(Collectors.toList());
    }

    // 2. 게시글 상세
    public MypagePostDto getPostById(Integer postId) {
        Object[] row = mypagePostDao.findPostDetail(postId);

        if (row == null || row.length < 9) {
            return null;
        }

        return MypagePostDto.builder()
                .id(getInt(row[0]))
                .title((String) row[1])
                .location((String) row[2])
                .status((String) row[3])
                .content((String) row[4])
                .price(getInt(row[5]))
                .area(getInt(row[6]))
                .startDate(getTimestamp(row[7]))
                .endDate(getTimestamp(row[8]))
                .build();
    }

    // 유틸: 정수형 안전 변환
    private Integer getInt(Object obj) {
        return obj != null ? ((Number) obj).intValue() : null;
    }

    // 유틸: Timestamp → LocalDateTime 안전 변환
    private java.time.LocalDateTime getTimestamp(Object obj) {
        return obj != null ? ((Timestamp) obj).toLocalDateTime() : null;
    }
}