package com.project.momo.service;

import com.project.momo.entity.ImageUrl;
import com.project.momo.entity.Member;
import com.project.momo.repository.ImageUrlRepository;
import com.project.momo.repository.MemberRepository;
import com.project.momo.security.oauth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ImageUrlRepository imageUrlRepository;

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
            member.setImageUrl(imageUrl);
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(member.getId(), null, Collections.emptyList()));
    }
}
