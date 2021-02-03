package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.dto.PathResult;
import subway.station.domain.Station;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FareCalculatorTest {
    Line l1;
    Station s1, s2;
    Section sc1;
    PathVertex vertex;
    PathVertices pathVertices;
    PathResult pathResult;
    FareCalculator fareCalculator;
    @BeforeEach
    void setUp(){
        s1 = new Station(1L, "s1");
        s2 = new Station(2L, "s2");
        l1 = new Line("l1", "c1", 0);
        l1.addSection(new Section(s1, s2, 5));
        vertex = new PathVertex(s1, Arrays.asList(l1));
        pathVertices = PathVertices.of(Arrays.asList(vertex));
        pathResult = new PathResult(pathVertices, 5);
        fareCalculator = new FareCalculator();
    }
    @Test
    void calculateTest(){

        Fare fare = FareCalculator.calculate(pathResult, 5);
        assertThat(fare.getFare()).isEqualTo(0);

        fare = FareCalculator.calculate(pathResult, 15);
        assertThat(fare.getFare()).isEqualTo(1070);

        fare = FareCalculator.calculate(pathResult, 30);
        assertThat(fare.getFare()).isEqualTo(1250);
    }
}