package com.project.momo.repository;

import com.project.momo.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Long> {
    List<State> findAllByCityId(long id);
}
