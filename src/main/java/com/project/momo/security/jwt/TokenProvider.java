package com.project.momo.security.jwt;

import com.project.momo.enums.TokenType;
import com.project.momo.security.userdetails.UserDetailsImpl;
import com.project.momo.service.AuthorizationService;
import com.project.momo.utils.JwtUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

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

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String createToken(Authentication authentication, TokenType tokenType) {
        JwtBuilder jwtBuilder = Jwts.builder();

        long now = (new Date()).getTime();
        Date expireAt = new Date();

        if (tokenType == TokenType.ACCESS) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            jwtBuilder.claim(JwtUtils.MEMBER_ID_CLAIM_NAME, userDetails.getId());
            expireAt = new Date(now + accessTokenValidTimeInMillis);
        } else if (tokenType == TokenType.REFRESH)
            expireAt = new Date(now + refreshTokenValidTimeInMillis);

        return jwtBuilder
                .setExpiration(expireAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String jwt) {
        Long memberId = jwtParser.parseClaimsJws(jwt).getBody().get(JwtUtils.MEMBER_ID_CLAIM_NAME, Long.class);
        return new UsernamePasswordAuthenticationToken(memberId, null, Collections.emptyList());
    }

    public boolean validate(String jwt) throws SignatureException, MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
        jwtParser.parseClaimsJws(jwt);
        return true;
    }

    public boolean validateRefreshToken(Long memberId, String jwt) {
        validate(jwt);
        return authorizationService.checkRefreshToken(memberId, jwt);
    }

    public String reissue(Long memberId) {
        long now = (new Date()).getTime();
        Date expireAt = new Date(now + accessTokenValidTimeInMillis);

        return Jwts.builder()
                .setExpiration(expireAt)
                .claim(JwtUtils.MEMBER_ID_CLAIM_NAME, memberId)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
