# Spring Security + JWT, REST API 예제

### Spring Security + JWT
- API 요청마다 Authorization header 의 JWT AccessToken 검증
  - 토큰 검증 과정에서 회원 정보 불러올 때, DB 조회 최소화를 위해 Redis 캐시 사용
    - Redis 캐시 저장소에 Member 객체 Map 형태로 저장
      - id, createDate, modifyDate, username, email, accessToken, authorities
  - 토큰 검증 성공시, 강제 로그인 처리

### REST API
>Article

| METHOD | URI            | 설명        |
|--------|----------------|-----------|
| GET    | /articles      | 게시물 목록    |
| POST   | /articles      | 게시물 등록    |
| GET    | /articles/{id} | 게시물 단건 조회 |
| PATCH  | /articles/{id} | 게시물 수정    |
| DELETE | /articles/{id} | 게시물 삭제    |

>Member

| METHOD | URI           | 설명        |
|--------|---------------|-----------|
| GET    | /member/me    | 회원 정보 조회  |
| POST   | /member/login | 로그인 |

### ResponseEntity 응답 객체
- 응답 객체 : ResponseEntity< RsData > 응답
- 응답 객체의 Body 에 들어갈 RsData 객체 정의(규격화하여 사용)

| ResponseEntity | 설명            |
|----------------|---------------|
| status         | HTTP 상태코드     |
| headers        | 헤더            |
| body           | 바디(응답 데이터 객체) |

| RsData | 설명                                 |
| --- |------------------------------------|
| resultCode | 응답 코드(HTTP 상태코드와 별개로 프론트 협업을 위해 사용) |
| msg | 응답 메시지                             |
| data | 실제 응답 데이터(프론트가 사용할 JSON 데이터)       |
| success | 성공 여부                              |
| fail | 실패 여부                              |