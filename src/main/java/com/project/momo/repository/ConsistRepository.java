package com.project.momo.repository;

import com.project.momo.common.constatnt.ClubRole;
import com.project.momo.entity.Consist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsistRepository extends JpaRepository<Consist, Long> {
    Optional<Consist> findByMemberIdAndClubId(long memberId, long clubId);

    int countAllByClubId(long clubId);

    boolean existsByMemberIdAndClubId(long memberId, long clubId);

    int countAllByMemberIdAndRole(long memberId, ClubRole clubRole);
}
