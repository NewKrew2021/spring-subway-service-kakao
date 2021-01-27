package subway.path;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Lines;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;
import subway.path.domain.Graph;
import subway.path.domain.Path;
import subway.station.domain.Station;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {

    private Path path;

    private Graph graph;

    private Station 강남역;
    private Station 교대역;
    private Station 양재역;
    private Station 남부터미널역;
    private Station 광교역;

    private Line 삼호선;
    private Line 이호선;
    private Line 신분당선;

    private Lines lines;

    /**
     *          10               5
     * 강남역 - *이호선* - 교대역 - *삼호선* - 남부터미널역
     * /                                     /
     * /                                     /
     * *신분당선*  ----   양재역      ---- *삼호선*
     * 20                 \               3
     *               *신분당선* 10
     *                    \
     *                  광교역
     */


    @BeforeEach
    void setPath() {
        강남역 = new Station(1L,"강남역");
        교대역 = new Station(2L,"교대역");
        양재역 = new Station(3L,"양재역");
        남부터미널역 = new Station(4L,"남부터미널역");
        광교역 = new Station(5L,"광교역");

        삼호선 = new Line(1L, "삼호선", "bg-red-200", 500);
        삼호선.getSections().addSection(new Section(교대역, 남부터미널역, 1, 5));
        삼호선.getSections().addSection(new Section(남부터미널역, 양재역, 1, 3));

        이호선 = new Line(2L, "이호선", "bg-red-300", 300);
        이호선.getSections().addSection(new Section(강남역, 교대역, 2, 10));

        신분당선 = new Line(3L, "신분당선", "bg-red-400", 600);
        신분당선.getSections().addSection(new Section(강남역, 양재역, 3, 20));
        신분당선.getSections().addSection(new Section(양재역, 광교역, 3, 10));

        lines = new Lines(Arrays.asList(삼호선, 이호선, 신분당선));
        graph = new Graph(Arrays.asList(강남역, 교대역, 양재역, 남부터미널역, 광교역), lines.getAllSections());
    }

    @Test
    void totalPathFeeTest() {
        /**
         * 강남역 --> 교대역 --> 남부터미널역 --> 양재역 --> 광교역
         * 거리 : 10 + 5 + 3 + 10 = 28km
         * 노선요금 : 300 + 500 + 600 = 1400
         * 거리요금 : 10~28km 400원
         * 총 요금 : 1250 + 1400 + 400 = 3050
         */
        Station sourceStation = 강남역;
        Station targetStation = 광교역;

        path = new Path(
                graph.getPathStations(sourceStation, targetStation),
                graph.getPathDistance(sourceStation, targetStation),
                lines.getTotalExtraFares()
        );

        assertThat(path.getTotalFare()).isEqualTo(3050);
    }

    @Test
    void totalPathFeeTestWithChildLoginMember() {
        Station sourceStation = 강남역;
        Station targetStation = 광교역;
        LoginMember loginMember = new LoginMember(1L, "aaa@test.com", 10);
        path = new Path(
                graph.getPathStations(sourceStation, targetStation),
                graph.getPathDistance(sourceStation, targetStation),
                lines.getTotalExtraFares()
        );

        /**
         * 총요금 3050 - (3050 - 350) * 0.5 = 1700
         */
        assertThat(path.getTotalFare(loginMember)).isEqualTo(1700);
    }

    @Test
    void totalPathFeeTestWithTeenagerLoginMember() {
        Station sourceStation = 강남역;
        Station targetStation = 광교역;
        LoginMember loginMember = new LoginMember(1L, "aaa@test.com", 15);
        path = new Path(
                graph.getPathStations(sourceStation, targetStation),
                graph.getPathDistance(sourceStation, targetStation),
                lines.getTotalExtraFares()
        );

        /**
         * 총요금 3050 - (3050 - 350) * 0.2 = 1700
         */
        assertThat(path.getTotalFare(loginMember)).isEqualTo(2510);
    }
}
