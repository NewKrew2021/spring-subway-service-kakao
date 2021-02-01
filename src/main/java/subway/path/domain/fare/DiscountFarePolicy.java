package subway.path.domain.fare;

import subway.common.domain.Fare;

public abstract class DiscountFarePolicy extends FarePolicy {
    public abstract Fare getFare();
}
