package com.project.momo.repository;

import com.project.momo.entity.Member;
import com.project.momo.security.consts.OauthType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByOauthTypeAndOauthId(OauthType oauthType, String oauthId);
}