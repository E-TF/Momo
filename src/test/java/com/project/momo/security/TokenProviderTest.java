package com.project.momo.security;

import com.project.momo.security.jwt.TokenProvider;
import com.project.momo.security.userdetails.UserDetailsImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacSigner;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;

@SpringBootTest(classes = {
        TokenProvider.class
})
public class TokenProviderTest {

    @Autowired
    TokenProvider tokenProvider;

    private UserDetailsImpl userDetails;
    private Authentication authentication;

    @Value("${jwt.token-valid-time-in-seconds}")
    private String tokenValidTimeInSeconds;

    @Value("${jwt.secret}")
    private String secret;

    @BeforeEach
    void init() {
        userDetails = new UserDetailsImpl(1L, "hi", "123");
        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());
    }

    @DisplayName("생성된 토큰은 공백이 될수 없다.")
    @Test
    void tokenCreationTest() {
        String token = tokenProvider.createToken(authentication);

        Assertions.assertThat(token).isNotBlank();
    }

    @DisplayName("Token의 Header는 {'alg':'HS512'}를 BASE64로 인코딩한 값이여야 한다.")
    @Test
    void tokenHeaderTest() {

        String token = tokenProvider.createToken(authentication);

        StringTokenizer stringTokenizer = new StringTokenizer(token, ".");
        String header = stringTokenizer.nextToken();

        String decodedHeader = new String(Decoders.BASE64.decode(header));

        Assertions.assertThat(decodedHeader).isEqualTo("{\"alg\":\"HS512\"}");
    }

    @DisplayName("Token의 Payload는 Long 타입의 memberId를 가지고 있어야하며 userDetails.id와 일치해야 한다.")
    @Test
    void tokenPayloadMemberIdTest() {

        String token = tokenProvider.createToken(authentication);
        String payload = token.split("\\.")[1];

        String decodedPayload = new String(Decoders.BASE64.decode(payload));
        System.out.println(decodedPayload);
        JSONObject jsonObject = new JSONObject(decodedPayload);
        Long memberId = jsonObject.getLong("memberId");

        Assertions.assertThat(memberId).isEqualTo(userDetails.getId());
    }

    @DisplayName("Token의 Payload는 exp(만료시간)을 가져야하며 현재시간과의 차이가 token-valid-time-in-seconds와 근사한 값(오차범위 10초)을 가져야한다.")
    @Test
    void tokenPayloadExpireAtTest() {


        String token = tokenProvider.createToken(authentication);
        String payload = token.split("\\.")[1];

        String decodedPayload = new String(Decoders.BASE64.decode(payload));
        JSONObject jsonObject = new JSONObject(decodedPayload);
        Date exp = new Date(jsonObject.getLong("exp") * 1000);

        //현재 시간과 만료시간(exp)의 차(seconds)를 10의 자리에서 반올림 한 값
        long diff = Math.round((exp.getTime() - new Date().getTime()) / 10000.0) * 10;

        Assertions.assertThat(exp).isAfter(new Date());
        Assertions.assertThat(diff).isEqualTo(Long.parseLong(tokenValidTimeInSeconds));
    }

    @DisplayName("Token의 Signature은 Header, Payload, secret으로 생성한 값과 일치해야한다.(단 =, +, / 제외)")
    @Test
    void tokenSignatureTest() {

        String token = tokenProvider.createToken(authentication);
        String[] splitToken = token.split("\\.");
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        MacSigner macSigner = new MacSigner(SignatureAlgorithm.HS512, key);

        String jwtWithoutSignature = splitToken[0] + '.' + splitToken[1];

        byte[] bytes = jwtWithoutSignature.getBytes(Charset.forName("US-ASCII"));
        byte[] sign = macSigner.sign(bytes);
        String signature = Encoders.BASE64.encode(sign)
                .replaceAll("=", "")
                .replaceAll("\\+", "-")
                .replaceAll("/", "_");

        Assertions.assertThat(signature).isEqualTo(splitToken[2]);

    }

    @DisplayName("생성한지 tokenValidTimeInSeconds이내의 Token은 유효해야한다.")
    @Test
    void tokenValidateTest() {

        String token = tokenProvider.createToken(authentication);

        Assertions.assertThat(tokenProvider.validate(token)).isTrue();
    }

    @DisplayName("생성한지 tokenValidTimeInSeconds이후의 Token은 유효하지 않아야한다.")
    @Test
    void tokenValidateFailTest() {

    }
}


//token 을 생성할때 사용되는 Authentication은 UsernamePasswordAuthenticationToken이다.
//이 Authentication은 AbstractUserDetailsAuthenticationProvider.createSuccessAuthentication에서 만들어진다.
//UserPasswordAuthenticationToken은 Principal, Credential, Authorities로 구성된다.
//Principal : UserService에서 retrieve된 UserDetails가 들어간다.
//Credential : null
//Authorities : Collections.emptyList()