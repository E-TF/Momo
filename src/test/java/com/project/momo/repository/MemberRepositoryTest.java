package com.project.momo.repository;

import com.project.momo.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void 회원저장() {
        Member member = new Member();
        memberRepository.save(member);

        Member findMember = memberRepository.findOne(member.getId());

        Assertions.assertThat(member).isEqualTo(findMember);
    }

}