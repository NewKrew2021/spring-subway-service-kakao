package subway.path.domain.fare;

import subway.common.domain.Distance;
import subway.common.domain.Fare;

public class ExtraFarePolicyByDistance extends ExtraFarePolicy {
    private static final Fare FARE_PER_DISTANCE = Fare.from(100);
    private static final Distance FIVE_KM = Distance.from(5);
    private static final Distance EIGHT_KM = Distance.from(8);
    private static final Distance TEN_KM = Distance.from(10);
    private static final Distance FIFTY_KM = Distance.from(50);
    private static final Distance FORTY_KM = Distance.from(40);

    private final FarePolicy farePolicy;
    private final Fare addend;

    public ExtraFarePolicyByDistance(FarePolicy farePolicy, Distance distance) {
        this.farePolicy = farePolicy;
        addend = calculate(distance);
    }

    private Fare calculate(Distance distance) {
        return getFareOver10(distance);
    }

    private Fare getFareOver10(Distance distance) {
        int count = getCountOver10Under50(distance) + getCountOver50(distance);
        return FARE_PER_DISTANCE.multiply(count);
    }

    private int getCountOver10Under50(Distance distance) {
        if (distance.isShorter(TEN_KM)) {
            return 0;
        }
        return (int) Math.ceil(Distance.min(distance.getDifference(TEN_KM), FORTY_KM).getDistance() / (double) FIVE_KM.getDistance());
    }

    private int getCountOver50(Distance distance) {
        if (distance.isShorter(FIFTY_KM)) {
            return 0;
        }
        return (int) Math.ceil(distance.getDifference(FIFTY_KM).getDistance() / (double) EIGHT_KM.getDistance());
    }

    @Override
    public Fare getFare() {
        return Fare.add(farePolicy.getFare(), addend);
    }
}
