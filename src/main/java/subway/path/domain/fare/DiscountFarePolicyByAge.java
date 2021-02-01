package subway.path.domain.fare;

import subway.common.domain.Age;
import subway.common.domain.AgeGroup;
import subway.common.domain.Fare;

public class DiscountFarePolicyByAge extends DiscountFarePolicy {
    private final FarePolicy farePolicy;
    private final Fare subtrahend;

    public DiscountFarePolicyByAge(FarePolicy farePolicy, Age age) {
        this.farePolicy = farePolicy;
        subtrahend = calculate(farePolicy.getFare(), age);
    }

    private Fare calculate(Fare fare, Age age) {
        AgeGroup ageGroup = AgeGroup.from(age);
        DiscountConstantsByAgeGroup discountConstants = DiscountConstantsByAgeGroup.from(ageGroup);
        return fare.sub(discountConstants.deductionAmount).multiply(discountConstants.discountRate);
    }

    @Override
    public Fare getFare() {
        return farePolicy.getFare().sub(subtrahend);
    }
}
