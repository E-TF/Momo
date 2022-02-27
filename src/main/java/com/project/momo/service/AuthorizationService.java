package com.project.momo.service;

import com.project.momo.entity.RefreshToken;
import com.project.momo.common.exception.JwtException;
import com.project.momo.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveRefreshToken(Long memberId, String refreshToken) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findById(memberId);
        if (optionalRefreshToken.isPresent()) {
            optionalRefreshToken.get().updateToken(refreshToken);
        } else {
            refreshTokenRepository.save(new RefreshToken(memberId, refreshToken));
        }
    }

    @Transactional
    public Long checkRefreshToken(String refreshToken) throws JwtException {
        RefreshToken findByToken = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new JwtException("조작된 Refresh Token 입니다."));
        if (findByToken.getToken().equals(refreshToken))
            return findByToken.getMemberId();

        throw new JwtException("조작된 Refresh Token 입니다.");
    }

}
