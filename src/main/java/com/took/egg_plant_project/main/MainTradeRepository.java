package com.took.egg_plant_project.main;

import com.took.egg_plant_project.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MainTradeRepository extends JpaRepository<Trade, Integer> {

    Optional<Trade> findByPost_Id(Integer postId);

    @Modifying
    @Transactional
    void deleteByPost_Id(Integer postId);
}

