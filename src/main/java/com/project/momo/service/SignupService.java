package com.project.momo.service;

import com.project.momo.dto.signup.SignupForm;
import com.project.momo.entity.Member;
import com.project.momo.exception.DuplicatedLoginIdException;
import com.project.momo.repository.MemberRepository;
import com.project.momo.utils.EncryptUtils;
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
    public void signup(SignupForm signupForm) throws DuplicatedLoginIdException{
        checkDuplicateLoginId(signupForm.getLoginId());
        String encryptedPassword = EncryptUtils.encrypt(signupForm.getPassword(), passwordEncoder);
        Member member = Member.createMember(
                signupForm.getLoginId(),
                encryptedPassword,
                signupForm.getName(),
                signupForm.getEmail(),
                signupForm.getPhoneNumber());

        memberRepository.save(member);
    }

    private void checkDuplicateLoginId(String loginId) throws DuplicatedLoginIdException{
        if (hasDuplicateLoginId(loginId)) {
            throw DuplicatedLoginIdException.getInstance();
        }
    }

    @Transactional
    public boolean hasDuplicateLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }
}
