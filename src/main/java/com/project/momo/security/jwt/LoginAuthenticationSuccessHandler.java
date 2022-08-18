package com.project.momo.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.momo.common.exception.ErrorDto;
import com.project.momo.common.exception.auth.InvalidOauthTypeException;
import com.project.momo.dto.signup.SignupOauthDetails;
import com.project.momo.entity.Member;
import com.project.momo.repository.MemberRepository;
import com.project.momo.security.consts.OauthType;
import com.project.momo.security.consts.TokenType;
import com.project.momo.security.oauth.GithubOauthAttributes;
import com.project.momo.security.oauth.OauthAttributes;
import com.project.momo.security.userdetails.UserDetailsImpl;
import com.project.momo.service.AuthorizationService;
import com.project.momo.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final AuthorizationService authorizationService;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    private final SignupService signupService;

    private static String FRONT_END_CALLBACK_URL_FORMAT = "http://localhost:3000/callBack/%s/%s";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        long memberId;
        if (authentication instanceof OAuth2AuthenticationToken) {  //oauth 로그인
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            try {
                OauthType oauthType = OauthType.toOauthType(oauthToken.getAuthorizedClientRegistrationId());
                String oauthId = authentication.getName();
                Optional<Member> optionalMember = memberRepository.findByOauthTypeAndOauthId(oauthType, oauthId);

                if (optionalMember.isEmpty()) { //최초 로그인 시
                    memberId = signupOauth(oauthToken, oauthType);
                } else
                    memberId = optionalMember.get().getId();

                JwtTokens jwtTokens = new JwtTokens(memberId);
                response.sendRedirect(String.format(
                        FRONT_END_CALLBACK_URL_FORMAT,
                        jwtTokens.accessToken,
                        jwtTokens.refreshToken));
                return;

            } catch (InvalidOauthTypeException exception) {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, exception);
                return;
            }
        } else {  //기본 로그인
            memberId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        }

        JwtTokens jwtTokens = new JwtTokens(memberId);
        authorizationService.saveRefreshToken(memberId, jwtTokens.refreshToken);

        sendAccessAndRefreshToken(response, jwtTokens);
    }

    private long signupOauth(OAuth2AuthenticationToken oauthToken, OauthType oauthType) {
        OauthAttributes oauthAttributes = null;
        switch (oauthType) {
            case GITHUB:
                oauthAttributes = GithubOauthAttributes.ofAttributes(oauthToken.getPrincipal().getAttributes());
                break;
            case GOOGLE:
                //추가 예정
                break;
            case KAKAO:
                //추가 예정
                break;
            case NAVER:
                //추가 예정
                break;
        }
        return signupService
                .signupOAuth(oauthType+oauthAttributes.getOauthId(), new SignupOauthDetails(oauthAttributes));
    }

    private void sendAccessAndRefreshToken(HttpServletResponse response, JwtTokens jwtTokens) {
        response.setHeader(TokenType.ACCESS.getTokenHeader(), jwtTokens.accessToken);
        response.setHeader(TokenType.REFRESH.getTokenHeader(), jwtTokens.refreshToken);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    private void sendError(HttpServletResponse response, int httpStatus, RuntimeException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus);
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorDto(exception)));
    }

    class JwtTokens{
        private String accessToken;
        private String refreshToken;

        public JwtTokens(long memberId) {
            this.accessToken = tokenProvider.createToken(memberId, TokenType.ACCESS);
            this.refreshToken = tokenProvider.createToken(memberId, TokenType.REFRESH);
        }
    }
}
