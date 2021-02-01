package subway.path;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import subway.line.domain.Line;
import subway.path.domain.fare.FarePolicy;
import subway.path.domain.fare.LineFarePolicy;

public class LineFareTest {
	
    @DisplayName("추가요금 있는 노선을 지나는 경우")
    @Test
    void lineFare() {
    	List<Line> lines = new ArrayList<>();
    	lines.add(new Line("1호선", "red", 0));
    	lines.add(new Line("2호선", "green", 750));
    	lines.add(new Line("3호선", "blue", 0));
    	
    	FarePolicy policy = new LineFarePolicy(lines);
    	int fare = policy.apply(1250, null, 0);
    	
    	assertThat(fare).isEqualTo(2000);
    }
}
