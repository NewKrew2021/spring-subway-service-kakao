package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.path.domain.path.Path;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {

    private List<Line> lines;
    private Station 강남역;
    private Station 양재역;
    private Station 교대역;
    private Station 잠실역;
    private Station 남부터미널역;
    private Station 고속버스터미널역;

    @BeforeEach
    public void setUp() {
        강남역 = new Station(1L, "강남역");
        양재역 = new Station(2L, "양재역");
        교대역 = new Station(3L, "교대역");
        잠실역 = new Station(4L, "잠실역");
        남부터미널역 = new Station(5L, "남부터미널역");
        고속버스터미널역 = new Station(6L, "고속버스터미널역");


        Line 신분당선 = new Line("신분당선", "bg-red-600", 900);
        Line 이호선 = new Line("이호선", "bg-red-600", 500);
        Line 삼호선 = new Line("삼호선", "bg-red-600", 0);


        신분당선.addSection(강남역, 양재역, 20);
        이호선.addSection(교대역, 강남역, 28);
        이호선.addSection(강남역, 잠실역, 38);
        삼호선.addSection(교대역, 남부터미널역, 5);
        삼호선.addSection(남부터미널역, 양재역, 2);


        lines = Arrays.asList(신분당선, 이호선, 삼호선);
    }

    /**
     * 교대역  --- *2호선*(28) ---   강남역   --- *2호선*(38) ---   잠실역
     * |                        |
     * *3호선*(5)                 *신분당선*(20)
     * |                        |
     * 남부터미널역  --- *3호선*(2) ---   양재역
     */
    @DisplayName("남부터미널역에서 강남역 어른이 가는 경우")
    @Test
    void testPath1() {
        // when
        Path path = Path.from(lines, 남부터미널역, 강남역);
        LoginMember 어른 = new LoginMember(1L, "test", 30);

        // then
        assertThat(path.getStations()).isEqualTo(Arrays.asList(남부터미널역, 양재역, 강남역));
        assertThat(path.getDistance()).isEqualTo(22);
        assertThat(path.getFare(어른)).isEqualTo(2450);
    }

    @DisplayName("교대역에서 잠실역을 어른이 가는 경우")
    @Test
    void testPath2() {
        //when
        Path path = Path.from(lines, 교대역, 잠실역);

        //then
        LoginMember 어른 = new LoginMember(1L, "test", 30);
        assertThat(path.getStations()).isEqualTo(Arrays.asList(교대역, 남부터미널역, 양재역, 강남역, 잠실역));
        assertThat(path.getDistance()).isEqualTo(65);
        assertThat(path.getFare(어른)).isEqualTo(3150);
    }

}
