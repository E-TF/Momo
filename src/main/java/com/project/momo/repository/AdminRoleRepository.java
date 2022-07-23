package com.project.momo.repository;

import com.project.momo.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRoleRepository extends JpaRepository<AdminRole, Long> {
    boolean existsByMemberId(long memberId);
}
