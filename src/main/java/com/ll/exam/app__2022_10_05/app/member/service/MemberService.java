package com.ll.exam.app__2022_10_05.app.member.service;

import com.ll.exam.app__2022_10_05.app.member.entity.Member;
import com.ll.exam.app__2022_10_05.app.member.repository.MemberRepository;
import com.ll.exam.app__2022_10_05.app.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public Member join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        memberRepository.save(member);

        return member;
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    // AccessToken 발급(발급된게 있으면 바로 리턴)
    @Transactional
    public String genAccessToken(Member member) {
        // 1. DB에서 AccessToken 조회
        String accessToken = member.getAccessToken();
        // 2. 만료시, 토큰 새로 발급
        if (StringUtils.hasLength(accessToken) == false ) {
            // 지금으로부터 100년간의 유효기간을 가지는 토큰을 생성, DB에 토큰 저장
            Map<String, Object> claims = member.getAccessTokenClaims();
            accessToken = jwtProvider.generateAccessToken(claims, 60L * 60 * 24 * 365 * 100);
            member.setAccessToken(accessToken);
        }

        return accessToken;
    }

    // 해당 토큰이 화이트 리스트에 있는지 검증
    public boolean verifyWithWhiteList(Member member, String token) {
        System.out.println("token = " + token);
        System.out.println("token = " + member.getAccessToken());
        return member.getAccessToken().equals(token);
    }

    @Cacheable("key1")
    public int getCachedInt() {
        System.out.println("getCachedInt 호출됨");
        return 5;
    }

    @CacheEvict("key1")
    public void deleteCacheKey1() {

    }
}
