package subway.path.domain.fare;

import java.util.Arrays;
import java.util.List;

import subway.line.domain.Line;
import subway.path.domain.path.Path;

public class FareCalculator {
	private final int INITIAL_FARE = 0;
	private List<Line> lines;

	public FareCalculator(List<Line> lines) {
		this.lines = lines;
	}

	private List<FarePolicy> getFarePolicies() {
		return Arrays.asList(new DistanceFarePolicy(), new LineFarePolicy(lines), new AgeFarePolicy());
	}

	public int calculate(Path path, int age) {
		int fare = INITIAL_FARE;

		for (FarePolicy policy : getFarePolicies()) {
			fare = policy.apply(fare, path, age);
		}

		return fare;
	}
}
