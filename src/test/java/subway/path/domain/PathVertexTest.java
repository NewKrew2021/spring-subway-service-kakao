package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.station.domain.Station;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class PathVertexTest {
    @DisplayName("PathVertex 객체의 데이터 저장 여부 확인")
    @Test
    void vertexTest(){
        Line l1 = new Line("l1", "c1", 0);
        PathVertex vertex = new PathVertex(new Station(1L, "s1"));
        assertThat(vertex.getStation().getName()).isEqualTo("s1");
    }
}