package com.project.momo.repository;

import com.project.momo.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findById(long clubId);
    Optional<Club> findByName(String clubName);
    boolean existsByName(String name);
}
