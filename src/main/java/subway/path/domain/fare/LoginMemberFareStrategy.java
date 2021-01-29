package subway.path.domain.fare;

import subway.common.domain.Age;
import subway.common.domain.Distance;
import subway.common.domain.Fare;
import subway.common.domain.AgeGroup;
import subway.member.domain.LoginMember;

public class LoginMemberFareStrategy extends DefaultFareStrategy {
    private final Fare fare;

    public LoginMemberFareStrategy(Distance distance, Fare extraFare, LoginMember loginMember) {
        super(distance, extraFare);
        this.fare = calculateFare(loginMember);
    }

    private Fare calculateFare(LoginMember loginMember) {
        return getDiscountedFareByAge(super.getFare(), loginMember.getAge());
    }

    private Fare getDiscountedFareByAge(Fare fare, Age age) {
        AgeGroup ageGroup = AgeGroup.from(age);
        DiscountConstantsByAgeGroup discountConstants = DiscountConstantsByAgeGroup.from(ageGroup);
        return fare.sub(fare.sub(discountConstants.deductionAmount).multiply(discountConstants.discountRate));
    }

    @Override
    public Fare getFare() {
        return fare;
    }
}
