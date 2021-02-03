package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.dto.PathResult;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PathTest {

    Path path;
    Station s1, s2, s3;
    Line l1, l2;
    Section sc1, sc2, sc3;
    List<Line> lines = new ArrayList<>();

    @BeforeEach
    void setUp(){
        s1 = new Station(1L, "s1");
        s2 = new Station(2L, "s2");
        s3 = new Station(3L, "s3");
        l1 = new Line(1L, "l1", "black");
        l2 = new Line(2L, "l2", "red");
        sc1 = new Section(s1, s2, 2);
        sc2 = new Section(s2, s3, 3);
        sc3 = new Section(s1, s3, 10);
        l1.addSection(sc1);
        l1.addSection(sc2);
        l2.addSection(sc3);
        lines.add(l1);
        lines.add(l2);
        path = new Path(lines);
        path.initPath(lines);
    }

    @DisplayName("최소 길이를 가지는 최적 경로를 구하는지 확인하는 테스트")
    @Test
    void findShortestPathDistanceTest(){
        PathResult result = path.findShortestPath(s1, s3);
        assertThat(result.getDistance()).isEqualTo(5);
    }

    @DisplayName("최소 길이를 가지는 최적 경로의 역의 수가 맞는지 확인하는 테스트")
    @Test
    void findShortestPathStationsTest(){
        PathResult result = path.findShortestPath(s1, s3);
        assertThat(result.getPathVertices().getPathVertexList()).hasSize(3);
    }
}