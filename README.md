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

<br>

## 요구사항 정리
- 1단계
    - 출발역과 도착역 사이의 최단 거리 경로를 구하는 API 구현
        - request & response
    ```
    HTTP/1.1 200
    Request method:	GET
    Request URI:	http://localhost:55494/paths?source=1&target=6
    Headers: 	Accept=application/json
    		    Content-Type=application/json; charset=UTF-8
    ```

    ```
    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Sat, 09 May 2020 14:54:11 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    {
        "stations": [
            {
                "id": 5,
                "name": "양재시민의숲역"
            },
            {
                "id": 4,
                "name": "양재역"
            },
            {
                "id": 1,
                "name": "강남역"
            },
            {
                "id": 2,
                "name": "역삼역"
            },
            {
                "id": 3,
                "name": "선릉역"
            }
        ],
        "distance": 40
    }
    ```
    - 검색 시 경로와 함께 총 거리를 출력(요금은 무시)
    - 한 노선에서 경로 찾기 뿐만 아니라 여러 노선의 환승도 고려하기
