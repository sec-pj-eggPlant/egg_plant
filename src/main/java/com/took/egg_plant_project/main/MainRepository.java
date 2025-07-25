package com.took.egg_plant_project.main;

import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainRepository extends JpaRepository<Post, Integer> {

    Page<Post> findByWriterRoleOrderByIdDesc(Role role, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.writer.role = :role AND p.status <> 'DONE' ORDER BY p.id DESC")
    Page<Post> findByWriterRoleAndStatusNotDone(@Param("role") Role role, Pageable pageable);


//    List<Post> findByWriterRoleOrderByCreatedAtDesc(Role role);

//    @Query("SELECT p FROM Post p WHERE p.writer.role = :role ORDER BY p.createdAt DESC")
//    List<Post> findByRole(@Param("role") Role role);
}
