# Spring Security + JWT, REST API 예제

### Spring Security + JWT
- API 요청마다 Authorization header의 JWT AccessToken 검증
  - 토큰 검증 후, 강제 로그인 처리(DB 접근X 방식)

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
