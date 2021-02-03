package subway.path.domain;

import org.junit.jupiter.api.Test;
import subway.line.domain.Line;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class FareByLineTest {
    @Test
    void lineTest(){
        Fare fare = new FareByLine().calculateFare(Arrays.asList(new Line("l1", "c1", 500), new Line("l2", "c2", 1000)));
        assertThat(fare.getFare()).isEqualTo(1000);
    }
}
