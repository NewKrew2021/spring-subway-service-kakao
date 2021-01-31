package subway.path.domain.fare;

import subway.path.domain.path.Path;

public class DistanceFarePolicy implements FarePolicy {

	@Override
	public int apply(int fare, Path path, int age) {
		DistanceFare distanceFare = DistanceFare.getDistanceFare(path.getDistance());
		return fare + distanceFare.calculateExtraFareByDistance(path.getDistance());
	}
}
