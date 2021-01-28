package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PathTest {
    /**
     * line1 : 300
     * line2 : 500
     * line3 : 1000
     * <p>
     * station1    --- line1[20] ---   station2
     * |                        |
     * line2[30]                  line1[70]
     * |                        |
     * station4  --- line3[10] ---   station3
     */

    private static LoginMember child = new LoginMember(1L, "a", 6);
    private static LoginMember baby = new LoginMember(1L, "a", 3);
    private static LoginMember teenager = new LoginMember(1L, "a", 13);
    private static LoginMember adult = new LoginMember(1L, "a", 20);

    private static Station station1 = new Station(1L, "station1");
    private static Station station2 = new Station(2L, "station2");
    private static Station station3 = new Station(3L, "station3");
    private static Station station4 = new Station(4L, "station4");

    private static Section section1to2 = new Section(station1, station2, 20);
    private static Section section2to3 = new Section(station2, station3, 70);
    private static Section section3to4 = new Section(station3, station4, 10);
    private static Section section4to1 = new Section(station4, station1, 30);

    private static Line line1;
    private static Line line2;
    private static Line line3;
    private static List<Line> lines;

    @BeforeEach
    public void setUp() {
        line1 = new Line(1L, "line1", "a"
                , 300, new Sections(new ArrayList<>(Arrays.asList(section1to2, section2to3))));
        line2 = new Line(2L, "line2", "b"
                , 500, new Sections(new ArrayList<>(Arrays.asList(section4to1))));
        line3 = new Line(1L, "line3", "c"
                , 1000, new Sections(new ArrayList<>(Arrays.asList(section3to4))));

        lines = new ArrayList<>(Arrays.asList(line1, line2, line3));
    }

    @DisplayName("출발부터 목적지로하는 그래프와 회원정보가 주어졌을 때 그에 맞는 최종 금액을 반환한다.")
    @Test
    public void Test1() {
        //given : 경로 station1 -> station4 -> station3  기본금액 1250원 라인 추가액 : 1000  거리 40 -> 추가액 : 600
        Path path = new Path(lines, station1, station3, adult);
        List<Station> expectedStations1 = new ArrayList<>(Arrays.asList(station1, station4, station3));

        //then
        assertThat(path.getFare()).isEqualTo(2850);
        assertThat(path.getDistance()).isEqualTo(40);
        assertThat(path.getStations()).isEqualTo(expectedStations1);
    }

}
