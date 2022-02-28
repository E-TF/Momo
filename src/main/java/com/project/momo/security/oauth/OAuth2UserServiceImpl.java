package com.project.momo.security.oauth;

import com.project.momo.security.consts.OauthType;
import com.project.momo.security.oauth.dto.GithubOAuthAttributes;
import com.project.momo.security.oauth.dto.OAuthAttributes;
import com.project.momo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberService memberService;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegateService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegateService.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuthAttributes oAuthAttributes = null;
        if (OauthType.GITHUB.equals(registrationId)) {
            oAuthAttributes = GithubOAuthAttributes.ofAttributes(attributes);
        } else {
            throw new AuthenticationException("여기는 500에러가 나야하네");
        }

        memberService.saveOrUpdate(oAuthAttributes);

        return new DefaultOAuth2User(Collections.emptyList(), oAuth2User.getAttributes(), oAuthAttributes.getNameAttributeKey());
    }
}
