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

## 경로 조회 기능

### 객체 정의
* [x] SubwayPathGraph 일급객체 제작
* [x] PathService 빈 생성체

### 출발역과 도착역 사이의 최단 거리 경로 계산
* [x] 검색 시 최단 거리 경로 구하기 (역과 구간 전체 데이터를 통해 jgrapht를 사용하여 구현)
* [x] 검색시 총 거리 구하기
* [x] 여러 노선의 환승도 고려한 검색

### 경로 조회 API
* [x] 경로 조회 요청 및 응답 구현 (PathController)

## 회원 인증 기능

### 토큰 API (AuthController)
* [x] 토큰 발급 요청 및 응답 (JWT 라이브러리 사용)

### 인증 기능
* [x] 멤버 관리 기능에 토큰 인증 로직 구현

### 즐겨찾기 API (FavoriteController)
* [ ] 즐겨찾기 생성 요청 및 응답
* [ ] 즐겨찾기 목록 조회 요청 및 응답
* [ ] 즐겨찾기 목록 삭제 요청 및 응답


## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

<br>

## 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/next-step/spring-subway-admin-kakao/issues) 에 등록해주세요 :)

<br>

## 📝 License

This project is [MIT](https://github.com/next-step/spring-subway-admin-kakao/blob/master/LICENSE) licensed.
