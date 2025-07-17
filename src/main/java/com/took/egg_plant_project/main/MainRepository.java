package com.took.egg_plant_project.main;

import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainRepository extends JpaRepository<Post, Integer> {

    List<Post> findByStatus(String status);

    List<Post> findByWriterId(Integer  writerId);

    @Query("SELECT p FROM Post p WHERE p.writer.role = :role ORDER BY p.createdAt DESC")
    List<Post> findByRole(@Param("role") Role role);
}
