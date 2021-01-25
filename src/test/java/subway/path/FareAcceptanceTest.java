package subway.path;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import subway.AcceptanceTest;
import subway.auth.dto.TokenResponse;
import subway.line.dto.LineResponse;
import subway.path.dto.PathResponse;
import subway.station.dto.StationResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.auth.AuthAcceptanceTest.로그인되어_있음;
import static subway.line.LineAcceptanceTest.지하철_노선_등록되어_있음;
import static subway.line.SectionAcceptanceTest.지하철_구간_등록되어_있음;
import static subway.member.MemberAcceptanceTest.회원_생성을_요청;
import static subway.station.StationAcceptanceTest.지하철역_등록되어_있음;

@DisplayName("지하철 경로 조회")
public class FareAcceptanceTest extends AcceptanceTest {
    public static final String 어른_이메일 = "adult@email.com";
    public static final String 청소년_이메일 = "youth@email.com";
    public static final String 어린이_이메일 = "child@email.com";
    public static final String 패스워드 = "password";

    private LineResponse 신분당선;
    private LineResponse 이호선;
    private LineResponse 삼호선;
    private StationResponse 강남역;
    private StationResponse 양재역;
    private StationResponse 교대역;
    private StationResponse 잠실역;
    private StationResponse 남부터미널역;
    private StationResponse 고속버스터미널역;
    private StationResponse 건대입구역;
    private TokenResponse 어른;
    private TokenResponse 청소년;
    private TokenResponse 어린이;

    /**
     * 교대역  --- *2호선*(28) ---   강남역   --- *2호선*(38) ---   잠실역  --- *2호선*(8) --- 건대입구역
     * |                        |
     * *3호선*(5)                 *신분당선*(80)
     * |                        |
     * 남부터미널역  --- *3호선*(3) ---   양재역
     */
    @BeforeEach
    public void setUp() {
        super.setUp();

        강남역 = 지하철역_등록되어_있음("강남역");
        양재역 = 지하철역_등록되어_있음("양재역");
        교대역 = 지하철역_등록되어_있음("교대역");
        잠실역 = 지하철역_등록되어_있음("잠실역");
        건대입구역 = 지하철역_등록되어_있음("건대입구역");
        남부터미널역 = 지하철역_등록되어_있음("남부터미널역");
        고속버스터미널역 = 지하철역_등록되어_있음("고속버스터미널역");

        신분당선 = 지하철_노선_등록되어_있음("신분당선", "bg-red-600", 강남역, 양재역, 80, 900);
        이호선 = 지하철_노선_등록되어_있음("이호선", "bg-red-600", 교대역, 강남역, 28, 500);
        삼호선 = 지하철_노선_등록되어_있음("삼호선", "bg-red-600", 교대역, 남부터미널역, 5, 0);

        지하철_구간_등록되어_있음(삼호선, 남부터미널역, 양재역, 3);
        지하철_구간_등록되어_있음(이호선, 강남역, 잠실역, 38);
        지하철_구간_등록되어_있음(이호선, 강남역, 잠실역, 38);
        지하철_구간_등록되어_있음(이호선, 잠실역, 건대입구역, 8);

        회원_생성을_요청(어른_이메일, 패스워드, 30);
        회원_생성을_요청(청소년_이메일, 패스워드, 18);
        회원_생성을_요청(어린이_이메일, 패스워드, 12);

        어른 = 로그인되어_있음(어른_이메일, 패스워드);
        청소년 = 로그인되어_있음(청소년_이메일, 패스워드);
        어린이 = 로그인되어_있음(어린이_이메일, 패스워드);
    }

    @DisplayName("기본 요금")
    @Test
    void defaultFare() {
        // when
        ExtractableResponse<Response> response = 최단_경로_검색_요청(교대역, 남부터미널역);

        // then
        최단_경로_요금_조회됨(response, 1250);
    }

    @DisplayName("10km 초과")
    @Test
    void over10() {
        // when
        ExtractableResponse<Response> response = 최단_경로_검색_요청(남부터미널역, 강남역);

        // then
        최단_경로_요금_조회됨(response, 2250);
        // 이호선 + 500원
        // 거리초과 + 500원 (33km)
    }

    @DisplayName("50km 초과")
    @Test
    void over50() {
        // when
        ExtractableResponse<Response> response = 최단_경로_검색_요청(교대역, 잠실역);

        // then
        최단_경로_요금_조회됨(response, 2750);
        // 이호선 + 500원
        // 거리초과 + 800원 (50km) + 200원 (66km - 50km)
    }

    @DisplayName("추가 요금")
    @Test
    void extraFare() {
        // when
        ExtractableResponse<Response> response = 최단_경로_검색_요청(잠실역, 건대입구역);

        // then
        최단_경로_요금_조회됨(response, 1750);
        // 이호선 + 500원
    }

    @DisplayName("추가 요금 2")
    @Test
    void extraFareTwo() {
        // when
        ExtractableResponse<Response> response = 최단_경로_검색_요청(양재역, 건대입구역);

        // then
        최단_경로_요금_조회됨(response, 3950);
        // 이호선 + 900원
        // 거리초과 + 800원 (50km) + 1000원 (126km - 50km)
    }

    @DisplayName("어린이 요금")
    @Test
    void childFare() {
        // when
        ExtractableResponse<Response> response = 토큰_포함_최단_경로_검색_요청(어린이, 남부터미널역, 교대역);

        // then
        최단_경로_요금_조회됨(response, 800);
        // 원래요금 : 1250원
        // 350 + 900 * 0.5 = 800
        // 어린이: 운임에서 350원을 공제한 금액의 50%할인
    }

    @DisplayName("어린이 요금 2")
    @Test
    void childFareTwo() {
        // when
        ExtractableResponse<Response> response = 최단_경로_검색_요청(교대역, 잠실역);

        // then
        최단_경로_요금_조회됨(response, 1550);
        // 이호선 + 500원
        // 거리초과 + 800원 (50km) + 200원 (66km - 50km)
        // 350 + 2400 * 0.5 = 1550
    }

    @DisplayName("청소년 요금")
    @Test
    void youthFare() {
        // when
        ExtractableResponse<Response> response = 토큰_포함_최단_경로_검색_요청(청소년, 남부터미널역, 교대역);

        // then
        최단_경로_요금_조회됨(response, 1070);
        // 원래요금 : 1250원
        // 350 + 900 * 0.8 = 1070
        // 청소년: 운임에서 350원을 공제한 금액의 20%할인
    }

    @DisplayName("청소년 요금 2")
    @Test
    void youthFareTwo() {
        // when
        ExtractableResponse<Response> response = 최단_경로_검색_요청(교대역, 잠실역);

        // then
        최단_경로_요금_조회됨(response, 2270);
        // 이호선 + 500원
        // 거리초과 + 800원 (50km) + 200원 (66km - 50km)
        // 350 + 2400 * 0.8 = 2270
    }

    @DisplayName("어른 요금")
    @Test
    void adultFare() {
        // when
        ExtractableResponse<Response> response = 토큰_포함_최단_경로_검색_요청(어른, 남부터미널역, 교대역);

        // then
        최단_경로_요금_조회됨(response, 1250);
        // 원래요금 : 1250원
    }

    private ExtractableResponse<Response> 최단_경로_검색_요청(StationResponse sourceStation, StationResponse targetStation) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(String.format("/paths?source=%d&target=%d", sourceStation.getId(), targetStation.getId()))
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 토큰_포함_최단_경로_검색_요청(TokenResponse tokenResponse, StationResponse sourceStation, StationResponse targetStation) {
        return RestAssured
                .given().log().all().auth().oauth2(tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(String.format("/paths?source=%d&target=%d", sourceStation.getId(), targetStation.getId()))
                .then().log().all()
                .extract();
    }

    private void 최단_경로_요금_조회됨(ExtractableResponse<Response> response, int fare) {
        PathResponse path = response.as(PathResponse.class);
        assertThat(path.getFare()).isEqualTo(fare);
    }
}