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

# 기능 목록
## Step 1 - 경로 조회 기능
* 출발역과 도착역 사이의 최단 거리 경로를 구하는 API
    * 경로와 함께 총 거리를 출력
    * 한 노선에서의 경로 찾기 뿐만 아니라 여러 노선의 환승도 고려해야함
    * 찾을 수 없는 경로에 대한 예외 처리

## Step 2 - 회원 인증 관련 기능
* 회원 로그인 API
  * 로그인 요청 시 토큰 발급
  * 토큰으로 회원 정보를 받아옴
* 즐겨찾기 API
  * 즐겨찾기 생성/삭제/조회 기능
  * 해당하는 회원의 로그인 정보 필요
  
## Step 3 - 요금 계산 기능
* 출발역과 도착역 사이의 요금을 계산하는 API
  * 경로 조회 시 요금도 함께 조회
  * 거리 별로 추가운임료 부과
    * 기본운임(10㎞ 이내) : 기본운임 1,250원
    * 10km초과∼50km까지(5km마다 100원)
    * 50km초과 시 (8km마다 100원)
  * 노선에 추가 요금이 부과되는 경우 고려
  * 로그인 사용자의 경우 연령별 할인 요금 적용
    * 청소년: 운임에서 350원을 공제한 금액의 20%할인
    * 어린이: 운임에서 350원을 공제한 금액의 50%할인
    * 6세 미만: 무임


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
