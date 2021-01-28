package subway.path.domain.fare;

import subway.member.domain.Age;
import subway.member.domain.LoginMember;
import subway.path.domain.fare.strategy.FareStrategyFactory;

public class Fare {
    private int fare;

    public Fare(Age age, FareParam fareParam) {
        int fare = FareStrategyFactory.createBy(fareParam)
                .stream()
                .mapToInt(strategy -> strategy.getExtraFare())
                .sum();
        this.fare = age.discount(fare);
    }

    public static Fare of(LoginMember loginMember, FareParam fareParam) {
        if (loginMember == null)
            return new Fare(Age.ADULT, fareParam);
        return new Fare(Age.getAge(loginMember.getAge()), fareParam);
    }

    public int getFare() {
        return fare;
    }
}
