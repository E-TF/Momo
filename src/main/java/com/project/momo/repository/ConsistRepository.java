package com.project.momo.repository;

import com.project.momo.common.constatnt.ClubRole;
import com.project.momo.entity.Consist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsistRepository extends JpaRepository<Consist, Long> {
    int countAllByClubId(long clubId);

    boolean existsByMemberIdAndClubId(long memberId, long clubId);

    int countAllByMemberIdAndRole(long memberId, ClubRole clubRole);
}
