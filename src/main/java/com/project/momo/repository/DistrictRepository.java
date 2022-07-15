package com.project.momo.repository;

import com.project.momo.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findAllByStateId(long stateId);
}
