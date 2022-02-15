package com.project.momo.security.userdetails;

import com.project.momo.entity.Member;
import com.project.momo.exception.LoginIdNotFoundException;
import com.project.momo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws LoginIdNotFoundException {

        if (memberRepository.findByLoginId(username).isPresent()) {
            Member member = memberRepository.findByLoginId(username).get();
            return new UserDetailsImpl(member.getId(), member.getLoginId(), member.getPassword());
        }

        throw new LoginIdNotFoundException();
    }
}
