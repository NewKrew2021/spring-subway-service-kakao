package subway.path.domain.fare;

import java.util.List;

import subway.line.domain.Line;
import subway.path.domain.path.Path;


public class LineFarePolicy implements FarePolicy {
	private List<Line> lines;
	
	public LineFarePolicy(List<Line> lines) {
		this.lines = lines;
	}

	@Override
	public int apply(int fare, Path path, int age) {
		int maxExtraFare = lines.stream()
				.mapToInt(Line::getExtraFare)
				.max()
				.orElse(0);
		return fare + maxExtraFare;
	}
}
