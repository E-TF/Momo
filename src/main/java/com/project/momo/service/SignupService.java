package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.signup.SignupOAuthDetails;
import com.project.momo.dto.signup.SignupRequest;
import com.project.momo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SignupService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest signupRequest) throws BusinessException {
        checkDuplicateLoginId(signupRequest.getLoginId());
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        memberRepository.save(signupRequest.toMember());
    }

    @Transactional
    public void signupOAuth(SignupOAuthDetails signupOAuthDetails) {
        memberRepository.save(signupOAuthDetails.toMember());
    }

    @Transactional(readOnly = true)
    public void checkDuplicateLoginId(String loginId) throws BusinessException {
        if (memberRepository.existsByLoginId(loginId)) {
            throw new BusinessException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
    }
}
