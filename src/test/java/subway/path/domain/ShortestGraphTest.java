package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ShortestGraphTest {

    /**
     * line1 : 300
     * line2 : 500
     * line3 : 1000
     *
     * station1    --- line1[20] ---   station2
     * |                        |
     * line2[30]                  line1[70]
     * |                        |
     * station4  --- line3[10] ---   station3
     */

    private static Station station1 = new Station(1L,"station1");
    private static Station station2 = new Station(2L,"station2");
    private static Station station3 = new Station(3L,"station3");
    private static Station station4 = new Station(4L,"station4");

    private static Section section1to2 = new Section(station1,station2,20);
    private static Section section2to3 = new Section(station2,station3,70);
    private static Section section3to4 = new Section(station3,station4,10);
    private static Section section4to1 = new Section(station4,station1,30);

    private static Line line1;
    private static Line line2;
    private static Line line3;
    private static List<Line> lines;

    @BeforeEach
    public void setUp() {
        line1 = new Line(1L,"line1","a"
                ,300, new Sections(new ArrayList<>(Arrays.asList(section1to2,section2to3))));
        line2 = new Line(2L,"line2","b"
                ,500, new Sections(new ArrayList<>(Arrays.asList(section4to1))));
        line3 = new Line(1L,"line3","c"
                ,1000, new Sections(new ArrayList<>(Arrays.asList(section3to4))));

        lines = new ArrayList<>(Arrays.asList(line1,line2,line3));
    }

    @DisplayName("주어진 노선과, 출발 목적역, 도착 목적역이 주어졌을 때 최단경로와 거리를 반환하는지 확인한다.")
    @Test
    public void Test1() {
        //given,when
        ShortestGraph shortestGraph1 = new ShortestGraph(lines,station1,station3);

        ShortestGraph shortestGraph2 = new ShortestGraph(lines,station2,station3);

        ShortestGraph shortestGraph3 = new ShortestGraph(lines,station2,station4);

        List<Station> expectedStations1 = new ArrayList<>(Arrays.asList(station1,station4,station3));
        List<Station> expectedStations2 = new ArrayList<>(Arrays.asList(station2,station1,station4,station3));
        List<Station> expectedStations3 = new ArrayList<>(Arrays.asList(station2,station1,station4));

        //then
        assertThat(shortestGraph1.getGraphPath().getWeight()).isEqualTo(4);
        assertThat(shortestGraph1.getGraphPath().getVertexList()).isEqualTo(expectedStations1);

        assertThat(shortestGraph2.getGraphPath().getWeight()).isEqualTo(6);
        assertThat(shortestGraph2.getGraphPath().getVertexList()).isEqualTo(expectedStations2);

        assertThat(shortestGraph3.getGraphPath().getWeight()).isEqualTo(5);
        assertThat(shortestGraph3.getGraphPath().getVertexList()).isEqualTo(expectedStations3);

    }

}
