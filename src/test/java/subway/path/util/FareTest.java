package subway.path.util;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FareTest {
    LoginMember loginMember;
    Line line;
    Line line2;
    List<Station> stations;
    Station station1;
    Station station2;
    Station station3;
    Station station4;
    Station station5;
    @BeforeEach
    void setUp() {
        station1 = new Station(1L, "잠실");
        station2 = new Station(2L, "강남");
        station3 = new Station(3L, "정자");
        station4 = new Station(4L, "판교");
        station5 = new Station(5L, "야탑");
        Section section1 = new Section(station1, station2);
        Section section2 = new Section(station2, station3);
        Section section3 = new Section(station3, station4);
        Section section4 = new Section(station3, station5);
        Sections sections1 = new Sections(Arrays.asList(section1, section2, section3));
        Sections sections2 = new Sections(Arrays.asList(section4));
        stations = Arrays.asList(station1, station2, station3, station4, station5);

        line = new Line(1L, "red", "신분당선", sections1, 300);
        line2 = new Line(2L, "blue", "분당선", sections2, 400);
        loginMember = new LoginMember();
    }

    @ParameterizedTest
    @CsvSource({"9,1250", "10,1250", "11,1350", "40,1850", "50,2050", "51,2150", "58,2150", "59,2250"})
    void 총거리_기본_요금_테스트(int totalDistance, int result) {
        assertThat(FareCalculator.getDistanceFare(totalDistance)).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource({"9,800", "10,800", "11,850", "40,1100", "50,1200", "51,1250", "58,1250", "59,1300"})
    void 어린이_요금_테스트(int totalDistance, int result) {
        int totalFare = FareCalculator.getDistanceFare(totalDistance);
        loginMember = new LoginMember(1L, "kakao@kakao.com", 8);
        assertThat(FareCalculator.getFinalFare(ComplimentaryAge.CHILD, totalFare)).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource({"9,1070", "10,1070", "11,1150", "40,1550", "50,1710", "51,1790", "58,1790", "59,1870"})
    void 청소년_요금_테스트(int totalDistance, int result) {
        int totalFare = FareCalculator.getDistanceFare(totalDistance);
        loginMember = new LoginMember(1L, "kakao@kakao.com", 17);
        assertThat(FareCalculator.getFinalFare(ComplimentaryAge.TEENAGER, totalFare)).isEqualTo(result);
    }
}
