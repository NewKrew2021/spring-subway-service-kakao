package subway.path.domain.fare;

import subway.common.domain.Fare;

public class ExtraFarePolicyByExtraFare extends ExtraFarePolicy{
    private final FarePolicy farePolicy;
    private final Fare addend;

    public ExtraFarePolicyByExtraFare(FarePolicy farePolicy, Fare extraFare) {
        this.farePolicy = farePolicy;
        this.addend = extraFare;
    }

    @Override
    public Fare getFare() {
        return Fare.add(farePolicy.getFare(), addend);
    }
}
