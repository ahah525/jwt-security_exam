package com.ll.exam.app__2022_10_05.app.security.filter;

import com.ll.exam.app__2022_10_05.app.member.entity.Member;
import com.ll.exam.app__2022_10_05.app.member.service.MemberService;
import com.ll.exam.app__2022_10_05.app.security.entity.MemberContext;
import com.ll.exam.app__2022_10_05.app.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String barerToken = request.getHeader("Authorization");
        // 토큰 유효성 검증
        if(barerToken != null) {
            String token = barerToken.substring("Bearer ".length());
            // 1. 1차 체크(정보가 변조되지 않았는지 검증)
            if(jwtProvider.verify(token)) {
                Map<String, Object> claims = jwtProvider.getClaims(token);
                // 캐시로 memberMap 조회
                String username = (String) claims.get("username");
                Map<String, Object> memberMap = memberService.getMemberMapByUsername__cached(username);
                // memberMap -> member 변환
                Member member = Member.fromMap(memberMap);

                // 2. 2차 체크(해당 엑세스 토큰이 화이트 리스트에 포함되는지 검증)
                if (memberService.verifyWithWhiteList(member, token)) {
                    // 강제 로그인 처리
                    forceAuthentication(member);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    // 강제 로그인 처리
    private void forceAuthentication(Member member) {
        MemberContext memberContext = new MemberContext(member);

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        memberContext,
                        null,
                        member.getAuthorities()
                );

        // 이후 컨트롤러 단에서 MemberContext 객체 사용O
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}