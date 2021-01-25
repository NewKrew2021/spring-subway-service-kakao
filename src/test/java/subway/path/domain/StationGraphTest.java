package subway.path.domain;

import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StationGraphTest {
    private final Station 강남역 = new Station(1L, "강남");
    private final Station 정자역 = new Station(2L, "정자");
    private final Station 미금역 = new Station(3L, "미금");
    private final Station 서현역 = new Station(4L, "서현");
    private final Station 역삼역 = new Station(5L, "역삼");
    private final Station 죽전역 = new Station(6L, "죽전");
    private final Station 야탑역 = new Station(7L, "야탑");

    private final Section 강남_정자 = new Section(강남역, 정자역, 5);
    private final Section 정자_미금 = new Section(정자역, 미금역, 5);
    private final Section 정자_서현 = new Section(정자역, 서현역, 3);
    private final Section 서현_역삼 = new Section(서현역, 역삼역, 3);
    private final Section 미금_서현 = new Section(미금역, 서현역, 1);
    private final Section 죽전_야탑 = new Section(죽전역, 야탑역, 10);

    private final Line 분당선 = new Line(1L, "분당선", "빨강", 100, new Sections(Arrays.asList(강남_정자, 정자_미금)));
    private final Line 중앙선 = new Line(2L, "중앙선", "노랑", 200, new Sections(Arrays.asList(정자_서현, 서현_역삼)));
    private final Line 일호선 = new Line(3L, "일호선", "주황", 300, new Sections(Arrays.asList(미금_서현)));
    private final Line 이호선 = new Line(4L, "이호선", "초록", 400, new Sections(Arrays.asList(죽전_야탑)));

    private final List<Line> lines = Arrays.asList(분당선, 중앙선, 일호선, 이호선);

    @Test
    public void graphTest_20살() {
        StationGraph stationGraph = new StationGraph(lines);

        PathInfo pathInfo = stationGraph.getPathInfo(강남역, 미금역, 20);
        assertThat(pathInfo.getDistance()).isEqualTo(9);
        assertThat(pathInfo.getStations()).isEqualTo(Arrays.asList(강남역, 정자역, 서현역, 미금역));
        assertThat(pathInfo.getFare()).isEqualTo(1550);
    }

    @Test
    public void graphTest_10살() {
        StationGraph stationGraph = new StationGraph(lines);

        PathInfo pathInfo = stationGraph.getPathInfo(강남역, 미금역, 10);
        assertThat(pathInfo.getDistance()).isEqualTo(9);
        assertThat(pathInfo.getStations()).isEqualTo(Arrays.asList(강남역, 정자역, 서현역, 미금역));
        assertThat(pathInfo.getFare()).isEqualTo(950);
    }

    @Test
    public void noGraphTest() {
        StationGraph stationGraph = new StationGraph(lines);
        assertThatThrownBy(() -> stationGraph.getPathInfo(죽전역, 강남역, 10)).isInstanceOf(RuntimeException.class);
    }

}
