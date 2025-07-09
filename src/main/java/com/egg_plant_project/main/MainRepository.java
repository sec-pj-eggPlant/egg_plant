package com.egg_plant_project.main;

import com.egg_plant_project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainRepository extends JpaRepository<Post, Integer> {

    List<Post> findByStatus(String status);

    List<Post> findByWriterId(Integer  writerId);
}
