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


## 구현 기능 목록

### 경로 조회 기능

* 요구사항
    * 출발역과 도착역 사이의 최단 거리 경로
    * 경로와 함께 총 거리 출력
    * 여러 노선 환승 가능
    
* 구현 기능
    * PathController
        * 최단 경로 요청 처리
    * PathService
        * 모든 노선의 모든 구간 불러오기 -> 그래프 생성
        * 최단 경로 및 거리 탐색
        * PathResponse에 최단 경로 Station 리스트와 거리 저장

### 회원과 인증 관련 기능

* 요구사항
    * 액세스 토큰 발급
    * 토큰을 확인해 로그인 정보 받기
        * 정보 수정, 삭제 동작시키기
    * 즐겨찾기 기능
    
* 구현 기능
    * 토큰 발급 및 로그인
        * AuthController
            * 로그인(토큰 발급) 요청 처리
            * /members/me 요청 시 토큰 확인 (AuthenticationPrincipal 사용)
        * AuthService
            * 토큰 발급 및 유효성 검증
        * AuthenticationPrincipalArgumentResolver
            * 토큰 검사 후 유효한 로그인의 경우 LoginMember 객체 생성 후 응답
    * 즐겨찾기
        * FavoriteController
            * 요청 시 토큰 확인 (AuthenticationPrincipal 사용)
            * 즐겨찾기 생성, 조회, 생성 요청 응답
        * FavoriteService
            * 생성: 해당 멤버의 FavoriteRequest을 Favorite에 담아 즐겨찾기 생성
            * 조회: DB 조회 결과를 FavoriteResponse에 담아 반환
            * 삭제: Favorite ID로 삭제
        * FavoriteDao
            * 생성: FAVORITE 테이블에 생성
            * 조회: FAVORITE 테이블에서 해당 유저 id에 해당하는 즐겨찾기 반환 (STATION과 join)
            * 삭제: FAVORITE 테이블에 삭제