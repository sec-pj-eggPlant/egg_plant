package com.took.egg_plant_project.admin.repository;

import com.took.egg_plant_project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminPostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByWriter_UserID(String userID);
}