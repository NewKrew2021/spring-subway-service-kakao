package subway.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import subway.AcceptanceTest;
import subway.auth.dto.TokenResponse;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.line.dto.LineResponse;
import subway.path.domain.fareStrategy.ChildFareStrategy;
import subway.path.domain.fareStrategy.DefaultFareStrategy;
import subway.path.domain.fareStrategy.TeenagerFareStrategy;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.auth.AuthAcceptanceTest.로그인되어_있음;
import static subway.line.LineAcceptanceTest.지하철_노선_등록되어_있음;
import static subway.line.SectionAcceptanceTest.지하철_구간_등록되어_있음;
import static subway.member.MemberAcceptanceTest.회원_생성을_요청;
import static subway.station.StationAcceptanceTest.지하철역_등록되어_있음;

public class FareStrategyTest extends AcceptanceTest {
    private Station 교대역;
    private Station 양재역;
    private Station 남부터미널역;
    private Section 교대양재;
    private Section 남부터미널양재;
    private Line 삼호선;

    @BeforeEach
    public void setUp() {
        super.setUp();

        교대역 = new Station(3L, "교대역");
        양재역 = new Station(4L, "양재역");
        남부터미널역 = new Station(5L, "남부터미널역");

        교대양재 = new Section(4L, 교대역, 양재역, 5);
        남부터미널양재 = new Section(5L, 남부터미널역, 양재역, 3);

        삼호선 = new Line(3L, "삼호선", "bg-red-600", 0, new Sections(Arrays.asList(교대양재, 남부터미널양재)));
    }

    @Test
    void defaultFare(){
        int fare = new DefaultFareStrategy(1250, 5, Arrays.asList(삼호선)).getFare();
        assertThat(fare).isEqualTo(1250);
    }

    @Test
    void childFare(){
        int fare = new ChildFareStrategy(1350).getFare();
        assertThat(fare).isEqualTo(850);
    }

    @Test
    void teenagerFare(){
        int fare = new TeenagerFareStrategy(1350).getFare();
        assertThat(fare).isEqualTo(1150);

    }
}
