package com.took.egg_plant_project.mypage.service;

import com.took.egg_plant_project.entity.Post;
import com.took.egg_plant_project.mypage.dao.MypagePostDao;
import com.took.egg_plant_project.mypage.dto.MypagePostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypagePostService {
    private final MypagePostDao mypagePostDao;

    public List<MypagePostDto> getMyPosts(Integer memberId) {
        List<Object[]> results = mypagePostDao.findMyPosts(memberId);
        return results.stream()
                .map(obj -> MypagePostDto.builder()
                        .id(((Number) obj[0]).intValue())
                        .title((String) obj[1])
                        .location((String) obj[2])
                        .price(obj[3] != null ? ((Number) obj[3]).intValue() : 0)
                        .area(obj[4] != null ? ((Number) obj[4]).intValue() : 0)
                        .startDate(obj[5] != null ? ((java.sql.Date) obj[5]).toLocalDate() : null)
                        .endDate(obj[6] != null ? ((java.sql.Date) obj[6]).toLocalDate() : null)
                        .status((String) obj[7])
                        .build()
                ).toList();
    }
    }

