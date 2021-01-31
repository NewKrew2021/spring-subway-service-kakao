package subway.path.domain;

import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.station.domain.Station;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PathVertexTest {
    @Test
    void vertexTest(){
        Line l1 = new Line("l1", "c1", 0);
        PathVertex vertex = new PathVertex(new Station(1L, "s1"), Arrays.asList(l1));
        assertThat(vertex.getStation().getName()).isEqualTo("s1");
        assertThat(vertex.getLineList()).containsExactly(l1);
    }
}