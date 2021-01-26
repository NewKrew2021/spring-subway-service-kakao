package subway.path.domain.fare;

import subway.member.domain.Age;
import subway.member.domain.AgeGroup;
import subway.member.domain.LoginMember;

public class LoginMemberFareStrategy extends GeneralFareStrategy {
    private final LoginMember loginMember;

    public LoginMemberFareStrategy(int distance, int extraFare, LoginMember loginMember) {
        super(distance, extraFare);
        this.loginMember = loginMember;
    }

    @Override
    public int getFare() {
        return getDiscountedFare(super.getFare());
    }

    private int getDiscountedFare(int fare) {
        Age age = new Age(loginMember.getAge());
        AgeGroup ageGroup = AgeGroup.of(age);

        DiscountConstantsByAgeGroup discountConstants = DiscountConstantsByAgeGroup.of(ageGroup);
        return fare - (int) ((fare - discountConstants.DEDUCTION_AMOUNT) * discountConstants.DISCOUNT_RATE);
    }
}
