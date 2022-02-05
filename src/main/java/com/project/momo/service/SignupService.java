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
        if(hasDuplicateLoginId(signupForm.getLoginId())) throw new DuplicatedLoginIdException();
        signupForm.setPassword(encryptPassword(signupForm.getPassword()));
        Member member = Member.createMember(signupForm);
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean hasDuplicateLoginId(String loginId) {
        if(memberRepository.findByLoginId(loginId).isPresent()) return true;
        return false;
    }
}
