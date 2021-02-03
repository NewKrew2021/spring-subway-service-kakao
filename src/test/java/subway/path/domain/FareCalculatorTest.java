package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.dto.PathResult;
import subway.station.domain.Station;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
class FareCalculatorTest {
    Line l1;
    Station s1, s2;
    PathVertex vertex1, vertex2;
    PathVertices pathVertices;
    PathResult pathResult;
    FareCalculator fareCalculator;
    @BeforeEach
    void setUp(){
        s1 = new Station(1L, "s1");
        s2 = new Station(2L, "s2");
        l1 = new Line("l1", "c1", 200);
        l1.addSection(new Section(s1, s2, 5));
        vertex1 = new PathVertex(s1, Arrays.asList(l1));
        vertex2 = new PathVertex(s2, Arrays.asList(l1));
        pathVertices = PathVertices.of(Arrays.asList(vertex1, vertex2));
        pathResult = new PathResult(pathVertices, 5);
        fareCalculator = new FareCalculator();
    }

    @DisplayName("6세 이하의 승객에 대한 요금 테스트")
    @Test
    void babyFareTest(){
        Fare fare = fareCalculator.calculate(pathResult, 5);
        assertThat(fare.getFare()).isEqualTo(0);
    }

    @DisplayName("6세 이상, 13세 미만의 승객에 대한 요금 테스트")
    @Test
    void youthFareTest(){
        Fare fare = fareCalculator.calculate(pathResult, 9);
        assertThat(fare.getFare()).isEqualTo(900);
    }

    @DisplayName("13세 이상, 19세 미만의 승객에 대한 요금 테스트")
    @Test
    void teenFareTest(){
        Fare fare = fareCalculator.calculate(pathResult, 15);
        assertThat(fare.getFare()).isEqualTo(1230);
    }

    @DisplayName("성인 승객에 대한 요금 테스트")
    @Test
    void adultFareTest(){
        Fare fare = fareCalculator.calculate(pathResult, 30);
        assertThat(fare.getFare()).isEqualTo(1450);
    }
}