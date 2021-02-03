package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class FareByLineTest {

    @DisplayName("포함된 노선 중, 가장 비싼 추가 요금 조회 테스트")
    @Test
    void extraFareTest(){
        Fare fare = new FareByLine().calculateFare(Arrays.asList(new Line("l1", "c1", 500), new Line("l2", "c2", 1000)));
        assertThat(fare.getFare()).isEqualTo(1000);
    }
}
