package com.ll.exam.app__2022_10_05;


import com.ll.exam.app__2022_10_05.cacheTest.service.CashTestService;
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
    private CashTestService cashTestService;

    @Test
    @DisplayName("캐시 사용")
    void t1() throws Exception {
        // OX
        int rs = cashTestService.getCachedInt();
        System.out.println(rs);

        rs = cashTestService.getCachedInt();
        System.out.println(rs);
    }

    @Test
    @DisplayName("캐시 삭제")
    void t2() throws Exception {
        // OXO
        int rs = cashTestService.getCachedInt();
        System.out.println(rs);

        rs = cashTestService.getCachedInt();
        System.out.println(rs);

        cashTestService.deleteCacheKey1();

        rs = cashTestService.getCachedInt();
        System.out.println(rs);
    }

    @Test
    @DisplayName("캐시 수정")
    void t3() throws Exception {
        // OXX
        int rs = cashTestService.getCachedInt();
        System.out.println(rs);

        rs = cashTestService.getCachedInt();
        System.out.println(rs);

        cashTestService.putCacheKey1();

        rs = cashTestService.getCachedInt();
        System.out.println(rs);
    }
}