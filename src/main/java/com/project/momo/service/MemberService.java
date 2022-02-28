package com.project.momo.service;

import com.project.momo.entity.Member;
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

    @Transactional
    public void saveOrUpdate(OAuthAttributes oAuthAttributes) {
        Optional<Member> optionalMember = memberRepository.findByOauthTypeAndOauthId(oAuthAttributes.getOauthType(), oAuthAttributes.getOauthId());
        Member member;
        if (optionalMember.isPresent()) {
            member = optionalMember.get();
            member.update(oAuthAttributes.getName(), oAuthAttributes.getEmail());
        } else {
            member = oAuthAttributes.toMember();
            memberRepository.save(member);
        }

        if (oAuthAttributes.getImageUrl().isPresent()) {
            member.setImageUrl(oAuthAttributes.getImageUrl().get());
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(member.getId(), null, Collections.emptyList()));
    }
}
