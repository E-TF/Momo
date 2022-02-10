package com.project.momo.security.jwt;

import com.project.momo.security.userdetails.UserDetailsImpl;
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

    private final String secret;
    private final long tokenValidTimeInMillis;
    private Key key;

    public TokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.token-valid-time-in-seconds}") long tokenValidTimeInSeconds) {
        this.secret = secret;
        this.tokenValidTimeInMillis = tokenValidTimeInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        long now = (new Date()).getTime();
        Date expireAt = new Date(now + tokenValidTimeInMillis);

        return Jwts.builder()
                .setExpiration(expireAt)
                .claim("memberId", userDetails.getId())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String refreshToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date expireAt = new Date(now + tokenValidTimeInMillis);

        return Jwts.builder()
                .setExpiration(expireAt)
                .claim("memberId", (Long) authentication.getPrincipal())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String jwt) {
        Long memberId = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody().get("memberId", Long.class);
        return new UsernamePasswordAuthenticationToken(memberId, null, Collections.emptyList());
    }

    public boolean validate(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (SignatureException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
