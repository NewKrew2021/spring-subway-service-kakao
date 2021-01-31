package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PathVerticesTest {
    Line l1;
    Station s1, s2;
    Section sc1;
    PathVertex vertex;
    PathVertices pathVertices;
    @BeforeEach
    void setUp(){
        s1 = new Station(1L, "s1");
        s2 = new Station(2L, "s2");
        l1 = new Line("l1", "c1", 0);
        l1.addSection(new Section(s1, s2, 5));
        vertex = new PathVertex(s1, Arrays.asList(l1));
        pathVertices = PathVertices.of(Arrays.asList(vertex));
    }

    @Test
    void fromTest(){
        PathVertices vertices = PathVertices.from(Arrays.asList(l1));
        assertTrue(vertices.getPathVertexList().size() != 0);
    }

    @Test
    void getTest(){
        assertThat(pathVertices.getPathVertexList()).isNotEmpty();
        assertThat(pathVertices.getPathVertexByStation(s1)).isNotNull();
    }
}