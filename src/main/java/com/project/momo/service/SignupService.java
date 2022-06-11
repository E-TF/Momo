package com.project.momo.service;

import com.project.momo.dto.signup.SignupOAuthDetails;
import com.project.momo.dto.signup.SignupOAuthRequest;
import com.project.momo.dto.signup.SignupRequest;
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
    public void signup(SignupRequest signupRequest) throws BusinessException {
        checkDuplicateLoginId(signupRequest.getLoginId());
        memberRepository.save(signupRequest.toMember(passwordEncoder));
    }

    @Transactional
    public void signupOAuth(SignupOAuthDetails signupOAuthDetails) {
        memberRepository.save(signupOAuthDetails.toMember());
    }

    public void checkDuplicateLoginId(String loginId) throws BusinessException {
        if (hasDuplicateLoginId(loginId)) {
            throw new BusinessException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
    }

    @Transactional
    boolean hasDuplicateLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }

}
