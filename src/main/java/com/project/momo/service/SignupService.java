package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.common.lock.DistributedLock;
import com.project.momo.common.lock.DistributedLockPrefix;
import com.project.momo.common.lock.LockName;
import com.project.momo.common.utils.PasswordManager;
import com.project.momo.dto.signup.SignupOauthDetails;
import com.project.momo.dto.signup.SignupRequest;
import com.project.momo.entity.Member;
import com.project.momo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SignupService {
    private final ObjectProvider<SignupService> provider;
    private final MemberRepository memberRepository;
    private final PasswordManager passwordManager;

    @DistributedLock(prefix = DistributedLockPrefix.MEMBER_LOGIN_ID)
    @Transactional
    public void signup(@LockName final String loginId, SignupRequest signupRequest) {
        provider.getObject().checkDuplicateLoginId(loginId);
        signupRequest.setPassword(passwordManager.encodePassword(signupRequest.getPassword()));
        memberRepository.save(signupRequest.toMember());
    }

    @DistributedLock(prefix = DistributedLockPrefix.MEMBER_LOGIN_ID)
    @Transactional
    public long signupOAuth(@LockName final String loginId, SignupOauthDetails signupOAuthDetails) {
        Member member = signupOAuthDetails.toMember();
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional(readOnly = true)
    public void checkDuplicateLoginId(String loginId) {
        if (memberRepository.existsByLoginId(loginId)) {
            throw new BusinessException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
    }
}
