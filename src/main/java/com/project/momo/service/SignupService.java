package com.project.momo.service;

import com.project.momo.dto.signup.SignupOAuthRequest;
import com.project.momo.dto.signup.SignupRequest;
import com.project.momo.entity.Member;
import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.repository.MemberRepository;
import com.project.momo.security.consts.OauthType;
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
        Member member = Member.createMember(
                signupRequest.getLoginId(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getName(),
                signupRequest.getEmail(),
                signupRequest.getPhoneNumber());

        memberRepository.save(member);
    }

    @Transactional
    public void signupOAuth(SignupOAuthRequest signupOAuthRequest) {
        Member member = Member.createOauth(
                OauthType.get(signupOAuthRequest.getOauthType()),
                signupOAuthRequest.getOauthId(),
                signupOAuthRequest.getName(),
                signupOAuthRequest.getEmail(),
                signupOAuthRequest.getPhoneNumber(),
                signupOAuthRequest.getImageUrl()
        );
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
