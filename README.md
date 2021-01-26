<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <img alt="npm" src="https://img.shields.io/badge/npm-%3E%3D%205.5.0-blue">
  <img alt="node" src="https://img.shields.io/badge/node-%3E%3D%209.3.0-blue">
  <a href="https://edu.nextstep.camp/c/R89PYi5H" alt="nextstep atdd">
    <img alt="Website" src="https://img.shields.io/website?url=https%3A%2F%2Fedu.nextstep.camp%2Fc%2FR89PYi5H">
  </a>
  <img alt="GitHub" src="https://img.shields.io/github/license/next-step/spring-subway-admin-kakao">
</p>

<br>

# 지하철 노선도 미션
카카오 신입사원 교육 - 스프링 과정 실습을 위한 지하철 노선도 애플리케이션

<br>

## 🚀 Getting Started

### Install
#### npm 설치
```
cd frontend
npm install
```
> `frontend` 디렉토리에서 수행해야 합니다.

### Usage
#### webpack server 구동
```
npm run dev
```
#### application 구동
```
./gradlew bootRun
```
<br>

## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

<br>

## 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/next-step/spring-subway-admin-kakao/issues) 에 등록해주세요 :)

<br>

## 📝 License

This project is [MIT](https://github.com/next-step/spring-subway-admin-kakao/blob/master/LICENSE) licensed.

## 추가 기능
### 추가 기능 정리
- 최단경로 조회 API /paths?source={sourceId}&target={targetId}
- 토큰 발급 API /login/token
- 토큰 인증 API /member/me
- 즐겨찾기 생성 API /favorites
- 즐겨찾기 목록 조회 API /favorites
- 즐겨찾기 삭제 API /favorites/{id}

### 작성 File
- AuthService       : 토큰 발급 service
- AuthController    : 토큰 발급 API controller
- AuthenticationPrincipalArgumentResolve : 토큰 조회시 유효성 확인 및 LoginMember객체 생성 추가
- NoneAuthorizationException : 잘못된 인증 토큰 request 예외처리
- FavoriteService   : 즐겨찾기 추가, 조회, 삭제 service
- FavoriteDao       : 즐겨찾기 추가, 조회, 삭제 dao
- Favorite          : 즐겨찾기 domain 객체
- FavoriteController    : 즐겨찾기 추가, 조회, 삭제 controller
- PathService       : 최단경로 조회 service
- Path              : 최단경로 domain 객체
- PathGraphEdge     : 최단경로 Edge 객체
- PathController    : 최단경로 조회 controller
- Line              : fare 추가
- LineDao           : fare 관련 요소 추가