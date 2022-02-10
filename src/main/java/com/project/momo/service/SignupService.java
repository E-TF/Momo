package com.project.momo.service;

import com.project.momo.dto.signup.SignupForm;
import com.project.momo.entity.Member;
import com.project.momo.exception.DuplicatedLoginIdException;
import com.project.momo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class SignupService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signup(SignupForm signupForm) {
        if (hasDuplicateLoginId(signupForm.getLoginId()))
            throw new DuplicatedLoginIdException();

        Member member = Member.createMember(signupForm);
        member.encryptPassword(passwordEncoder);

        return memberRepository.save(member);
    }

    private boolean hasDuplicateLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }
}
