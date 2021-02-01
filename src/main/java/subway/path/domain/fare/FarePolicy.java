package subway.path.domain.fare;

import subway.path.domain.path.Path;

public interface FarePolicy {
	int apply(int fare, Path path, int age);
}
