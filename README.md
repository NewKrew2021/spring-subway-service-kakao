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

## 목차

1. 미션 소개
    1. [1단계](#1단계--경로-조회-기능)
    2. [2단계](#2단계--회원과-인증-관련-기능)
    3. [3단계](#3단계--요금-계산-기능)
2. [실행 방법](#실행-방법)
3. [참고 링크](#참고-링크)
    1. 코드 리뷰 과정
    2. 버그 신고
    3. 라이선스

---

## 미션 소개

### 1단계 : 경로 조회 기능

미션 설명

* 출발역과 도착역 사이의 최단 거리 경로를 구하는 API를 구현하기
* 검색 시 경로와 함께 총 거리를 출력하기(요금은 무시)
* 한 노선에서 경로 찾기 뿐만 아니라 여러 노선의 환승도 고려하기
* 미리 제공된 프론트엔드 코드를 바탕으로 기능이 잘 동작하도록 완성하기

기능 요구 사항

* 경로 조회 API 구현하기

### 2단계 : 회원과 인증 관련 기능

미션 설명

* JWT 라이브러리를 활용하여 액세스 토큰 발급 기능을 구현하기
* 발급한 토큰을 이용하여 로그인이 필요한 기능(회원 정보 수정/삭제) 요청 시 포함하여 보내고 이를 이용하여 기능이 동작하도록 리팩토링 하기
* 즐겨찾기 기능을 구현하기
* 미리 제공된 프론트엔드 코드를 바탕으로 기능이 잘 동작하도록 완성하기

기능 요구 사항

* 토큰 발급 API 구현하기
* 토큰을 통한 인증 - 내 정보 기능
  * `/members/me` 요청 시 토큰을 확인하여 로그인 정보를 받아올 수 있도록 하기
  * `@AuthenticationPrincipal`과 `AuthenticationPrincipalArgumentResolver`을 활용하기
* 즐겨 찾기 기능 구현하기

### 3단계 : 요금 계산 기능

미션설명

* 경로 조회 시 요금도 함께 조회할 수 있도록 기능 구현하기
* 요금은 거리별 요금 정책, 노선별 추가 요금 정책, 연령별 요금 할인 정책을 적용

기능 요구 사항

* 거리별 요금 정책
  * 기본운임(10km  이내): 기본운임 1,250원
  * 이용 거리초과 시 추가운임 부과
    * 10km초과 ~ 50km까지 (5km마다 100원)
    * 50km초과 시 (8km마다 100원)
* 노션별 추가 요금 정책
  * 노선에 추가 요금 필드를 추가
  * 추가 요금이 있는 노선을 이용할 경우 측정된 요금에 추가
    * ex) 900원 추가 요금이 있는 노선 8km 이용 시: 1,250원 + 900원 -> 2,150원
    * ex) 900원 추가 요금이 있는 노선 12km 이용 시: 1,250원 + 100원 + 900원 -> 2,250원
  * 경로 중 추가 요금이 있는 노선을 환승하여 이용할 경우, 가장 높은 금액의 추가 요금만 적용
    * ex) 0원, 500원, 900원의 추가 요금이 있는 노선들을 경유하여 8km 이용 시: 1,250원 + 900원 -> 2,150원
* 로그인 사용자의 경우 연령별 요금 할인 적용
  * 6세 미만: 무임
  * 6세 이상 ~ 13세 미만: 운임에서 350원을 공제한 금액의 50% 할인
  * 13세 이상 ~ 19세 미만: 운임에서 350원을 공제한 금액의 20% 할인

## 실행 방법

### Install

#### npm 설치

```console
cd frontend
npm install
```

> `frontend` 디렉토리에서 수행해야 합니다.

### Usage

#### webpack server 구동

```console
npm run dev
```

#### application 구동

```console
./gradlew bootRun
```

---

## 참고 링크

### ✏️ Code Review Process

[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

### 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/next-step/spring-subway-admin-kakao/issues) 에 등록해주세요 :)

### 📝 License

This project is [MIT](https://github.com/next-step/spring-subway-admin-kakao/blob/master/LICENSE) licensed.
