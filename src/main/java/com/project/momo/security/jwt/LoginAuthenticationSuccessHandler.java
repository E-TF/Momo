package com.project.momo.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.momo.common.exception.ErrorDto;
import com.project.momo.common.exception.auth.InvalidOauthTypeException;
import com.project.momo.entity.Member;
import com.project.momo.repository.MemberRepository;
import com.project.momo.security.consts.OauthType;
import com.project.momo.security.consts.TokenType;
import com.project.momo.security.oauth.GithubOAuthAttributes;
import com.project.momo.security.oauth.OAuthAttributes;
import com.project.momo.security.userdetails.UserDetailsImpl;
import com.project.momo.service.AuthorizationService;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Long memberId;
        if (authentication instanceof OAuth2AuthenticationToken) {  //oauth 로그인
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            try {
                OauthType oauthType = OauthType.toOauthType(oauthToken.getAuthorizedClientRegistrationId());
                String oauthId = authentication.getName();
                Optional<Member> optionalMember = memberRepository.findByOauthTypeAndOauthId(oauthType, oauthId);

                if (optionalMember.isEmpty()) { //최초 로그인 시
                    OAuthAttributes oAuthAttributes = null;
                    switch (oauthType) {
                        case GITHUB:
                            oAuthAttributes = GithubOAuthAttributes.ofAttributes(oauthToken.getPrincipal().getAttributes());
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

                    String tempToken = tokenProvider.createToken(null, TokenType.ACCESS);
                    response.getWriter().write(objectMapper.writeValueAsString(oAuthAttributes));
                    response.setHeader(TokenType.ACCESS.getTokenHeader(), tempToken);
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                    return;
                }
                memberId = optionalMember.get().getId();
            } catch (InvalidOauthTypeException exception) {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, exception);
                return;
            }
        } else {  //기본 로그인
            memberId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        }

        String accessToken = tokenProvider.createToken(memberId, TokenType.ACCESS);
        String refreshToken = tokenProvider.createToken(memberId, TokenType.REFRESH);
        authorizationService.saveRefreshToken(memberId, refreshToken);

        sendAccessAndRefreshToken(response, accessToken, refreshToken);
    }

    private void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setHeader(TokenType.ACCESS.getTokenHeader(), accessToken);
        response.setHeader(TokenType.REFRESH.getTokenHeader(), refreshToken);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    private void sendError(HttpServletResponse response, int httpStatus, RuntimeException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus);
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorDto(exception)));
    }
}
