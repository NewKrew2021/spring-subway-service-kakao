package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;
import subway.line.domain.Sections;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GraphTest {
    private Station 강남역 = new Station(1L, "강남");
    private Station 정자역 = new Station(2L, "정자");
    private Station 미금역 = new Station(3L, "미금");
    private Station 서현역 = new Station(4L, "서현");
    private Station 역삼역 = new Station(5L, "역삼");
    private Station 죽전역 = new Station(6L, "죽전");
    private Station 야탑역 = new Station(7L, "야탑");
    private Station 서울숲역 = new Station(8L, "서울숲");
    private Station 왕십리역 = new Station(9L, "왕십리");
    private Station 청량리역 = new Station(10L, "청량리");
    private Station 회기역 = new Station(11L, "회기");
    private Station 한양대역 = new Station(12L, "한양대");
    private Station 안암역 = new Station(13L, "안암");


    private Section 강남_정자 = new Section(강남역, 정자역, 5);
    private Section 정자_미금 = new Section(정자역, 미금역, 5);
    private Section 정자_서현 = new Section(정자역, 서현역, 3);
    private Section 서현_역삼 = new Section(서현역, 역삼역, 3);
    private Section 미금_서현 = new Section(미금역, 서현역, 1);
    private Section 죽전_야탑 = new Section(죽전역, 야탑역, 10);
    private Section 서울숲_왕십리 = new Section(서울숲역, 왕십리역, 10);
    private Section 한양대_왕십리 = new Section(한양대역, 왕십리역, 3);
    private Section 왕십리_청량리_경의선 = new Section(왕십리역, 청량리역, 10);
    private Section 왕십리_청량리_수인분당선 = new Section(왕십리역, 청량리역, 3);
    private Section 청량리_회기 = new Section(청량리역, 회기역, 10);
    private Section 청량리_안암 = new Section(청량리역, 안암역, 3);


    private Line 분당선 = new Line(1L, "분당선", "빨강", 0, new Sections(Arrays.asList(강남_정자, 정자_미금)));
    private Line 중앙선 = new Line(2L, "중앙선", "노랑", 0, new Sections(Arrays.asList(정자_서현, 서현_역삼)));
    private Line 일호선 = new Line(3L, "일호선", "주황", 0, new Sections(Arrays.asList(미금_서현)));

    private Line 이호선 = new Line(4L, "이호선", "초록", 0, new Sections(Arrays.asList(죽전_야탑)));

    private Line 경의선 = new Line(5L, "경의선", "파랑", 0, new Sections(Arrays.asList(서울숲_왕십리, 왕십리_청량리_경의선, 청량리_회기)));
    private Line 수인분당선 = new Line(6L, "수인분당선", "진노랑", 0, new Sections(Arrays.asList(한양대_왕십리, 왕십리_청량리_수인분당선, 청량리_안암)));


    @DisplayName("출발지에서 목적지로 가는 경로가 존재하지 않는 경우")
    @Test
    public void noGraphTest() {
        //given
        List<Line> lines = Arrays.asList(분당선, 이호선);
        Graph graph = new Graph(lines);

        //when, then
        assertThatThrownBy(() ->
                graph.getPath(죽전역, 강남역)
        ).isInstanceOf(RuntimeException.class);
    }

    @DisplayName("최단거리: 다른 노선으로 두번 갈아타야 하는 경우")
    @Test
    public void graph_test_1() {
        //given
        List<Line> lines = Arrays.asList(분당선, 중앙선, 일호선);
        Graph graph = new Graph(lines);

        //when
        Path path = graph.getPath(강남역, 미금역);

        //then
        assertThat(path.getDistance()).isEqualTo(9);
        assertThat(path.getStations()).isEqualTo(Arrays.asList(강남역, 정자역, 서현역, 미금역));
    }

    @DisplayName("최단거리: 거쳐가는 역들은 같으나, 중간에 다른 section으로 갈아타야 하는 경우")
    @Test
    public void graph_test_2() {
        //given
        List<Line> lines = Arrays.asList(경의선, 수인분당선);
        Graph graph = new Graph(lines);

        //when
        Path path = graph.getPath(서울숲역, 회기역);

        //then
        assertThat(path.getDistance()).isEqualTo(23);
        assertThat(path.getStations()).isEqualTo(Arrays.asList(서울숲역, 왕십리역, 청량리역, 회기역));
    }
}
