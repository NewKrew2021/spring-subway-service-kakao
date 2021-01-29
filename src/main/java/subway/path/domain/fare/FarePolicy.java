package subway.path.domain.fare;

import subway.common.domain.Fare;

public class FarePolicy {
    private static final Fare BASIC_FARE = Fare.from(1250);

    public Fare getFare() {
        return BASIC_FARE;
    }
}
