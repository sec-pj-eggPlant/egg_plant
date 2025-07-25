package com.took.egg_plant_project.admin.service;

import com.took.egg_plant_project.admin.repository.AdminPostRepository;
import com.took.egg_plant_project.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminMemberDetailService {
    private final AdminPostRepository adminPostRepository;

    public List<Post> getPostsByUserId(String userId) {
        return adminPostRepository.findByWriter_UserID(userId);
    }
}
