package com.took.egg_plant_project.BDJ;

import com.took.egg_plant_project.entity.ForwardingParcel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForwardingParcelRepository extends JpaRepository<ForwardingParcel, Integer> {
}
