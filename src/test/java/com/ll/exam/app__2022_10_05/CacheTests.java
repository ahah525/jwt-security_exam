package com.ll.exam.app__2022_10_05;


import com.ll.exam.app__2022_10_05.cacheTest.service.CacheTestService;
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
    private CacheTestService cacheTestService;

    @Test
    @DisplayName("캐시 사용")
    void t1() throws Exception {
        // OX
        int rs = cacheTestService.getCachedInt();
        System.out.println(rs);

        rs = cacheTestService.getCachedInt();
        System.out.println(rs);
    }

    @Test
    @DisplayName("캐시 삭제")
    void t2() throws Exception {
        // OXO
        int rs = cacheTestService.getCachedInt();
        System.out.println(rs);

        rs = cacheTestService.getCachedInt();
        System.out.println(rs);

        cacheTestService.deleteCacheKey1();

        rs = cacheTestService.getCachedInt();
        System.out.println(rs);
    }

    @Test
    @DisplayName("캐시 수정")
    void t3() throws Exception {
        // OXX
        int rs = cacheTestService.getCachedInt();
        System.out.println(rs);

        rs = cacheTestService.getCachedInt();
        System.out.println(rs);

        cacheTestService.putCacheKey1();

        rs = cacheTestService.getCachedInt();
        System.out.println(rs);
    }


    @Test
    @DisplayName("더하기 캐시")
    void t4() throws Exception {
        // OOX
        int rs = cacheTestService.plus(10, 20);
        System.out.println(rs);

        rs = cacheTestService.plus(5, 2);
        System.out.println(rs);

        rs = cacheTestService.plus(10, 20);
        System.out.println(rs);
    }
}