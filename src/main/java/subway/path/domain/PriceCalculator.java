package subway.path.domain;

import subway.line.domain.DistancePolicy;
import subway.member.domain.LoginMember;
import subway.member.domain.LoginMemberType;

public class PriceCalculator {
    public static int calculateResult(LoginMember loginMember, ResultPath resultPath) {
        return getResultPrice(loginMember, resultPath);
    }

    public static int getResultPrice(LoginMember loginMember, ResultPath resultPath) {
        int defaultPrice = getPrice(resultPath);
        if (loginMember.getType() == LoginMemberType.KID) {
            return 0;
        }
        if (loginMember.getType() == LoginMemberType.CHILD) {
            return defaultPrice - (defaultPrice - Price.FARE_FREE_PRICE.getValue()) / Price.RATE_HALF.getValue();
        }
        if (loginMember.getType() == LoginMemberType.ADOLESCENT) {
            return defaultPrice - (defaultPrice - Price.FARE_FREE_PRICE.getValue()) / Price.RATE_FIFTH.getValue();
        }
        return defaultPrice;
    }

    public static int getPrice(ResultPath resultPath) {
        int distance = resultPath.getDistance();
        if (distance <= DistancePolicy.MIN_DISTANCE.getValue()) {
            return Price.DEFAULT_PRICE.getValue() + resultPath.getMaxExtraFare().getValue();
        }
        if (distance <= DistancePolicy.MAX_DISTANCE.getValue()) {
            return Price.DEFAULT_PRICE.getValue() +
                    under50Bonus(distance) * Price.ADDITIONAL_PRICE.getValue() +
                    resultPath.getMaxExtraFare().getValue();
        }
        return Price.DEFAULT_PRICE.getValue() +
                (DistancePolicy.MAX_DISTANCE.getValue() - DistancePolicy.MIN_DISTANCE.getValue()) / 5
                        * Price.ADDITIONAL_PRICE.getValue() +
                over50Bonus(distance) * Price.ADDITIONAL_PRICE.getValue() +
                resultPath.getMaxExtraFare().getValue();
    }

    public static int under50Bonus(int distance) {
        return (distance - DistancePolicy.MIN_DISTANCE.getValue())
                / DistancePolicy.PER_NEAR_DISTANCE.getValue()
                + ((distance - DistancePolicy.MIN_DISTANCE.getValue())
                % DistancePolicy.PER_NEAR_DISTANCE.getValue() == 0 ? 0 : 1);
    }

    public static int over50Bonus(int distance) {
        return (distance - DistancePolicy.MAX_DISTANCE.getValue())
                / DistancePolicy.PER_FAR_DISTANCE.getValue()
                + ((distance - DistancePolicy.MIN_DISTANCE.getValue())
                % DistancePolicy.PER_FAR_DISTANCE.getValue() == 0 ? 0 : 1);
    }
}
