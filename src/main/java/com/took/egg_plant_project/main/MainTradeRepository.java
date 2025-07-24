package com.took.egg_plant_project.main;

import com.took.egg_plant_project.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MainTradeRepository extends JpaRepository<Trade, Integer> {
    Optional<Trade> findByPostId(Integer postId);
    void deleteByPostId(Integer postId);
}
