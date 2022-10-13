package com.ll.exam.app__2022_10_05;

import com.ll.exam.app__2022_10_05.app.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CacheTests {
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("캐시 사용")
    void t1() throws Exception {
        // O
        int rs = memberService.getCachedInt();
        System.out.println(rs);

        rs = memberService.getCachedInt();
        System.out.println(rs);
    }

    @Test
    @DisplayName("캐시 삭제")
    void t2() throws Exception {
        // OXO
        int rs = memberService.getCachedInt();
        System.out.println(rs);

        rs = memberService.getCachedInt();
        System.out.println(rs);

        memberService.deleteCacheKey1();

        rs = memberService.getCachedInt();
        System.out.println(rs);
    }
}