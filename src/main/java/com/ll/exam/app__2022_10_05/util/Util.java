package com.ll.exam.app__2022_10_05.util;

import com.ll.exam.app__2022_10_05.app.base.dto.RsData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class Util {
    // 인자 값들을 map 형태로 반환
    public static<K, V> Map<K, V> mapOf(Object... args) {
        Map<K, V> map = new LinkedHashMap<>();

        int size = args.length / 2;

        for (int i = 0; i < size; i++) {
            int keyIndex = i * 2;
            int valueIndex = keyIndex + 1;

            K key = (K) args[keyIndex];
            V value = (V) args[valueIndex];

            map.put(key, value);
        }
        return map;
    }

    public static class spring {
        public static <T> ResponseEntity<RsData> responseEntityOf(RsData<T> rsData) {
            return responseEntityOf(rsData, null);
        }

        public static <T> ResponseEntity<RsData> responseEntityOf(RsData<T> rsData, HttpHeaders headers) {
            HttpStatus httpStatus = rsData.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            // body, header, httpStatus
            return new ResponseEntity<>(rsData, headers, httpStatus);
        }

        // 들어온 인자를 (key, value) 형태의 HttpHeaders 로 반환
        public static HttpHeaders httpHeadersOf(String... args) {
            HttpHeaders headers = new HttpHeaders();

            // (key, value)
            Map<String, String> map = Util.mapOf(args);

            for(String key : map.keySet()) {
                String value = map.get(key);
                headers.set(key, value);
            }

            return headers;
        }
    }
}
