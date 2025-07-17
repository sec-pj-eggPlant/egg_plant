package com.took.egg_plant_project.BDJ;

import com.took.egg_plant_project.entity.DeliveryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Integer> {
    boolean existsByTrackingNumber(String trackingNumber);
}
