package com.ll.exam.app__2022_10_05.app.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.exam.app__2022_10_05.app.base.entity.BaseEntity;
import com.ll.exam.app__2022_10_05.util.Util;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String accessToken;

    public Member(long id) {
        super(id);
    }

    // claim (Map)정보 -> Member 로 변환
    public static Member fromJwtClaims(Map<String, Object> jwtClaims) {
        long id = 0;
        if(jwtClaims.get("id") instanceof Long) {
            // Long -> long
            id = (long) jwtClaims.get("id");
        } else if(jwtClaims.get("id") instanceof Integer) {
            // Integer -> int -> long
            id = (long) (int) jwtClaims.get("id");
        }

        LocalDateTime createDate = (LocalDateTime) jwtClaims.get("createDate");
        LocalDateTime modifyDate = (LocalDateTime) jwtClaims.get("createDate");

        //TODO: jwtClaims.get("createDate") 가 이미 LocalDateTime 이다..?
        // List -> LocalDateTime
//        if (jwtClaims.get("createDate") instanceof List) {
//            createDate = Util.date.bitsToLocalDateTime((List<Integer>) jwtClaims.get("createDate"));
//        }
//        if (jwtClaims.get("modifyDate") instanceof List) {
//            modifyDate = Util.date.bitsToLocalDateTime((List<Integer>) jwtClaims.get("modifyDate"));
//        }
        String username = (String) jwtClaims.get("username");
        String email = (String) jwtClaims.get("email");
        String accessToken = (String) jwtClaims.get("accessToken");

        return Member.builder()
                .id(id)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .username(username)
                .email(email)
                .accessToken(accessToken)
                .build();
    }

    // Map -> Member 변환
    public static Member fromMap(Map<String, Object> map) {
        return fromJwtClaims(map);
    }

    // TODO: 나중에 씀
    // 현재 회원이 가지고 있는 권한들을 List<GrantedAuthority> 형태로 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("MEMBER"));

        return authorities;
    }

    // AccessToken 발급을 위해 회원 정보를 바탕으로 claim map 객체 만들어 반환
    public Map<String, Object> getAccessTokenClaims() {
        return Util.mapOf(
                "id", getId(),
                "createDate", getCreateDate(),
                "modifyDate", getModifyDate(),
                "username", getUsername(),
                "email", getEmail(),
                "authorities", getAuthorities()
        );
    }

    // Member -> Map 변환
    public Map<String, Object> toMap() {
        return Util.mapOf(
                "id", getId(),
                "createDate", getCreateDate(),
                "modifyDate", getModifyDate(),
                "username", getUsername(),
                "email", getEmail(),
                "accessToken", getAccessToken(),
                "authorities", getAuthorities()
        );
    }
}
