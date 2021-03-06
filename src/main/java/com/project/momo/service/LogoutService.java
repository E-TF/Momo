package com.project.momo.service;

import com.project.momo.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class LogoutService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void logout(Long memberId) {
        if (refreshTokenRepository.findById(memberId).isPresent())
            refreshTokenRepository.deleteById(memberId);
    }

}
