package subway.path.domain.fare;

import subway.path.domain.path.Path;

public class AgeFarePolicy implements FarePolicy {

	@Override
	public int apply(int fare, Path path, int age) {
		AgeFare ageFare = AgeFare.getAgeFare(age);
		return fare - ageFare.calculateDiscountFareByAge(fare);
	}
}
