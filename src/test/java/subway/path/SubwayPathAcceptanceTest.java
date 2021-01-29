package subway.path;

import com.google.common.collect.Lists;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import subway.AcceptanceTest;
import subway.line.dto.LineResponse;
import subway.path.dto.PathResponse;
import subway.station.dto.StationResponse;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.line.LineAcceptanceTest.지하철_노선_등록되어_있음;
import static subway.line.SectionAcceptanceTest.지하철_구간_등록되어_있음;
import static subway.station.StationAcceptanceTest.지하철역_등록되어_있음;

@DisplayName("지하철 경로 조회")
public class SubwayPathAcceptanceTest extends AcceptanceTest {
    private LineResponse 신분당선;
    private LineResponse 이호선;
    private LineResponse 삼호선;
    private StationResponse 강남역;
    private StationResponse 양재역;
    private StationResponse 교대역;
    private StationResponse 남부터미널역;

    /**
     * 교대역    --- *2호선* ---   강남역
     * |                        |
     * *3호선*                   *신분당선*
     * |                        |
     * 남부터미널역  --- *3호선* ---   양재
     */
    @BeforeEach
    public void setUp() {
        super.setUp();

        강남역 = 지하철역_등록되어_있음("강남역");
        양재역 = 지하철역_등록되어_있음("양재역");
        교대역 = 지하철역_등록되어_있음("교대역");
        남부터미널역 = 지하철역_등록되어_있음("남부터미널역");

        신분당선 = 지하철_노선_등록되어_있음("신분당선", "bg-red-600", 강남역, 양재역, 10, LocalTime.of(6, 0), LocalTime.of(22, 0), 10);
        이호선 = 지하철_노선_등록되어_있음("이호선", "bg-red-600", 교대역, 강남역, 10, LocalTime.of(6, 5), LocalTime.of(22, 0), 10);
        삼호선 = 지하철_노선_등록되어_있음("삼호선", "bg-red-600", 교대역, 양재역, 5, LocalTime.of(6, 0), LocalTime.of(22, 0), 60);

        지하철_구간_등록되어_있음(삼호선, 교대역, 남부터미널역, 3);
    }

    @DisplayName("두 역의 최단 거리 경로를 조회한다.")
    @Test
    void findPathByDistance() {
        //when
        ExtractableResponse<Response> response =
                거리_경로_조회_요청(교대역.getId(), 양재역.getId(), LocalDateTime.of(2021, 1, 26, 6, 30));

        //then
        적절한_경로_응답됨(response, Lists.newArrayList(교대역, 남부터미널역, 양재역));
        총_거리와_소요_시간과_도착시간이_함께_응답됨(response, 5, LocalDateTime.of(2021, 1, 26, 7, 5));
    }

    @DisplayName("두 역의 최단 시간 경로를 조회한다.")
    @Test
    void findPathByArrivalTime() {
        //when
        ExtractableResponse<Response> response =
                시간_경로_조회_요청(교대역.getId(), 양재역.getId(), LocalDateTime.of(2021, 1, 26, 6, 30));

        //then
        적절한_경로_응답됨(response, Lists.newArrayList(교대역, 강남역, 양재역));
        총_거리와_소요_시간과_도착시간이_함께_응답됨(response, 20, LocalDateTime.of(2021, 1, 26, 7, 0));
    }

    public static ExtractableResponse<Response> 거리_경로_조회_요청(long source, long target, LocalDateTime time) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/paths?source={sourceId}&target={targetId}&time={time}",
                        source, target, time.format(DateTimeFormatter.ofPattern("uuuuMMddhhmm")))
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 시간_경로_조회_요청(long source, long target, LocalDateTime time) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/paths?source={sourceId}&target={targetId}&type=ARRIVAL_TIME&time={time}",
                        source, target, time.format(DateTimeFormatter.ofPattern("uuuuMMddhhmm")))
                .then().log().all()
                .extract();
    }

    public static void 적절한_경로_응답됨(ExtractableResponse<Response> response, ArrayList<StationResponse> expectedPath) {
        PathResponse pathResponse = response.as(PathResponse.class);

        List<Long> stationIds = pathResponse.getStations().stream()
                .map(StationResponse::getId)
                .collect(Collectors.toList());

        List<Long> expectedPathIds = expectedPath.stream()
                .map(StationResponse::getId)
                .collect(Collectors.toList());

        assertThat(stationIds).containsExactlyElementsOf(expectedPathIds);
    }

    public static void 총_거리와_소요_시간과_도착시간이_함께_응답됨(ExtractableResponse<Response> response, int totalDistance, LocalDateTime time) {
        PathResponse pathResponse = response.as(PathResponse.class);
        assertThat(pathResponse.getDistance()).isEqualTo(totalDistance);
        assertThat(pathResponse.getArrivalAt()).isEqualTo(time.format(DateTimeFormatter.ofPattern("uuuuMMddhhmm")));
    }
}
