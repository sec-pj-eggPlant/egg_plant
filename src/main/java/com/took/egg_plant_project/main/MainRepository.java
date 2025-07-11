package com.took.egg_plant_project.main;

import com.took.egg_plant_project.constant.Role;
import com.took.egg_plant_project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MainRepository extends JpaRepository<Post, Integer> {

    List<Post> findByStatus(String status);

    List<Post> findByWriterId(Integer  writerId);

    @Query("SELECT p FROM Post p " +
            "WHERE (:status IS NULL OR p.status = :status) " +
            "AND (:location IS NULL OR p.location LIKE %:location%) " +
            "AND (:price IS NULL OR p.price <= :price) " +
            "AND (:area IS NULL OR p.area >= :area) " +
            "AND (:startDate IS NULL OR p.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR p.endDate <= :endDate) " +
            "AND (:keyword IS NULL OR p.title LIKE %:keyword% OR p.content LIKE %:keyword%) " +
            "AND (:role IS NULL OR p.writer.role = :role)")
    List<Post> findByFilters(@Param("status") String status,
                             @Param("location") String location,
                             @Param("price") Integer price,
                             @Param("area") Integer area,
                             @Param("startDate") LocalDate startDate,
                             @Param("endDate") LocalDate endDate,
                             @Param("keyword") String keyword,
                             @Param("role") Role role);
}
