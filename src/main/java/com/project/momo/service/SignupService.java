package com.project.momo.service;

import com.project.momo.dto.signup.SignupForm;
import com.project.momo.entity.Member;
import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
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
    public void signup(SignupForm signupForm) throws BusinessException {
        checkDuplicateLoginId(signupForm.getLoginId());
        Member member = Member.createMember(
                signupForm.getLoginId(),
                passwordEncoder.encode(signupForm.getPassword()),
                signupForm.getName(),
                signupForm.getEmail(),
                signupForm.getPhoneNumber());

        memberRepository.save(member);
    }

    private void checkDuplicateLoginId(String loginId) throws BusinessException {
        if (hasDuplicateLoginId(loginId)) {
            throw new BusinessException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
    }

    @Transactional
    public boolean hasDuplicateLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }
}
