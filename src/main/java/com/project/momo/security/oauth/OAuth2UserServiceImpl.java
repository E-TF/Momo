package com.project.momo.security.oauth;

import com.project.momo.entity.ImageUrl;
import com.project.momo.entity.Member;
import com.project.momo.repository.ImageUrlRepository;
import com.project.momo.repository.MemberRepository;
import com.project.momo.security.oauth.dto.GithubOAuthAttributes;
import com.project.momo.security.oauth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String GITHUB = "github";
    private static final String GOOGLE = "google";
    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";

    private final MemberRepository memberRepository;
    private final ImageUrlRepository imageUrlRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegateService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegateService.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeKey = oAuth2User.getName();
        System.out.println(userNameAttributeKey);
        System.out.println(attributes);
        OAuthAttributes oAuthAttributes = null;
        if (registrationId.equals(GITHUB)) {
            oAuthAttributes = GithubOAuthAttributes.ofAttributes(attributes);
        }
        if(oAuthAttributes!=null)
            saveOrUpdate(oAuthAttributes);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("인증 시점에 시큐리티 컨텍스트 안에 뭐가 들어있는지 확인" + authentication);
        return new DefaultOAuth2User(Collections.emptyList(), oAuth2User.getAttributes(), oAuthAttributes.getNameAttributeKey());
    }

    @Transactional
    public void saveOrUpdate(OAuthAttributes oAuthAttributes) {
        Optional<Member> optionalMember = memberRepository.findByOauthTypeAndOauthId(oAuthAttributes.getOauthType(), oAuthAttributes.getOauthId());
        Member member;
        if (optionalMember.isPresent()) {
            ImageUrl imageUrl = optionalMember.get().getImageUrl();
            imageUrl.update(oAuthAttributes.getImageUrl());
            optionalMember.get().update(oAuthAttributes.getName(), oAuthAttributes.getEmail(), imageUrl);
            member = optionalMember.get();
        } else {
            member = oAuthAttributes.toMember();
            memberRepository.save(member);
            ImageUrl imageUrl = ImageUrl.ofUrl(oAuthAttributes.getImageUrl(), member.getId());
            imageUrlRepository.save(imageUrl);
            //TODO
            member.setImageUrl(imageUrl);
        }
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(member.getId(), null, Collections.emptyList()));
    }

}
