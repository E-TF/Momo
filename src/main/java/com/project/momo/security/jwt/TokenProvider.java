package com.project.momo.security.jwt;

import com.project.momo.common.exception.auth.JwtException;
import com.project.momo.security.consts.JwtConst;
import com.project.momo.security.consts.OauthType;
import com.project.momo.security.consts.TokenType;
import com.project.momo.security.role.Role;
import com.project.momo.service.AuthorizationService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {

    private final int VALID_TIME_TO_MILLS = 1000;

    private final String secret;
    private final long accessTokenValidTimeInMillis;
    private final long refreshTokenValidTimeInMillis;
    private final AuthorizationService authorizationService;

    private JwtParser jwtParser;
    private Key key;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.access-token-valid-time-in-seconds}") long accessTokenValidTimeInSeconds,
                         @Value("${jwt.refresh-token-valid-time-in-seconds}") long refreshTokenValidTimeInSeconds,
                         AuthorizationService authorizationService
    ) {
        this.secret = secret;
        this.accessTokenValidTimeInMillis = accessTokenValidTimeInSeconds * VALID_TIME_TO_MILLS;
        this.refreshTokenValidTimeInMillis = refreshTokenValidTimeInSeconds * VALID_TIME_TO_MILLS;
        this.authorizationService = authorizationService;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String createToken(Long memberId, TokenType tokenType) {
        JwtBuilder jwtBuilder = Jwts.builder();

        long now = (new Date()).getTime();
        Date expireAt = new Date();

        if (tokenType == TokenType.ACCESS) {
            jwtBuilder.claim(JwtConst.MEMBER_ID_CLAIM_NAME, memberId);
            expireAt = new Date(now + accessTokenValidTimeInMillis);
        } else if (tokenType == TokenType.REFRESH)
            expireAt = new Date(now + refreshTokenValidTimeInMillis);

        return jwtBuilder
                .setExpiration(expireAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String jwt) {
        Long memberId = jwtParser.parseClaimsJws(jwt).getBody().get(JwtConst.MEMBER_ID_CLAIM_NAME, Long.class);
        if (memberId == null) {
            return new UsernamePasswordAuthenticationToken(null, null, Role.getTemp());
        }
        return new UsernamePasswordAuthenticationToken(memberId, null, Role.getUser());
    }

    public boolean validate(String jwt) throws JwtException {
        try {
            jwtParser.parseClaimsJws(jwt);
        } catch (SignatureException | MalformedJwtException e) {
            throw new JwtException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT 토큰이 잘못되었습니다.");
        }
        return true;
    }

    public Long validateRefreshToken(String jwt) throws JwtException {
        try {
            validate(jwt);
        } catch (SignatureException | MalformedJwtException e) {
            throw new JwtException("잘못된 Refresh JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 Refresh JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("지원되지 않는 Refresh JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new JwtException("Refresh JWT 토큰이 잘못되었습니다.");
        }
        return authorizationService.checkRefreshToken(jwt);
    }

    public String reissue(Long memberId) {
        return createToken(memberId, TokenType.ACCESS);
    }
}
