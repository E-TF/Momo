package com.project.momo.repository;

import com.project.momo.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findAllByStateId(long stateId);
}
