package com.project.momo.repository;

import com.project.momo.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원저장() {
        //given
        Member member = Member.createMember("loginId", "password",
                "test", "test@test.com", "00000000000", null);

        //when
        memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(IllegalStateException::new);
        List<Member> members = memberRepository.findAll();
        Assertions.assertThat(member).isEqualTo(findMember);
        Assertions.assertThat(members.size()).isEqualTo(1);
    }

}