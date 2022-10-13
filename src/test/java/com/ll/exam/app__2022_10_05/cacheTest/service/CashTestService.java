package com.ll.exam.app__2022_10_05.cacheTest.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CashTestService {
    @Cacheable("key1")
    public int getCachedInt() {
        System.out.println("getCachedInt 호출됨");
        return 5;
    }

    @CacheEvict("key1")
    public void deleteCacheKey1() {

    }

    @CachePut("key1")
    public int putCacheKey1() {
        return 10;
    }
}
