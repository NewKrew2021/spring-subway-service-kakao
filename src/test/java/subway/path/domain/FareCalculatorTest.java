package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.dto.Fare;
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
        vertex1 = new PathVertex(s1);
        vertex2 = new PathVertex(s2);
        pathVertices = PathVertices.of(Arrays.asList(vertex1, vertex2));
        pathResult = new PathResult(pathVertices, 5);
        fareCalculator = new FareCalculator();
    }

    @DisplayName("유아 승객 총 합산 요금 확인")
    @Test
    void babyFareTest(){
        Fare fare = fareCalculator.calculate(pathResult, Arrays.asList(200), 5);
        assertThat(fare.getFare()).isEqualTo(0);
    }

    @DisplayName("어린이 승객 총 합산 요금 확인")
    @Test
    void youthFareTest(){
        Fare fare = fareCalculator.calculate(pathResult, Arrays.asList(200),9);
        assertThat(fare.getFare()).isEqualTo(900);
    }

    @DisplayName("청소년 승객 총 합산 요금 확인")
    @Test
    void teenFareTest(){
        Fare fare = fareCalculator.calculate(pathResult, Arrays.asList(200),15);
        assertThat(fare.getFare()).isEqualTo(1230);
    }

    @DisplayName("성인 승객 총 합산 요금 확인")
    @Test
    void adultFareTest(){
        Fare fare = fareCalculator.calculate(pathResult, Arrays.asList(200),30);
        assertThat(fare.getFare()).isEqualTo(1450);
    }
}