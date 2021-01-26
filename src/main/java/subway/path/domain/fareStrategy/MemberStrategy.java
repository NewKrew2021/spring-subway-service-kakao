package subway.path.domain.fareStrategy;

import subway.member.domain.LoginMember;

public class MemberStrategy implements FareStrategy {

    private static final int TEENAGER_MIN_AGE = 13;
    private static final int TEENAGER_MAX_AGE = 19;
    private static final int CHILD_MIN_AGE = 6;
    private static final int TAX_CREDIT = 350;
    private static final double TEENAGER_DISCOUNT_RATE = 0.2;
    private static final double CHILD_DISCOUNT_RATE = 0.5;

    private int fare;
    private LoginMember loginMember;

    public MemberStrategy(int fare, LoginMember loginMember) {
        this.fare = fare;
        this.loginMember = loginMember;
    }

    @Override
    public int getFare() {
        if (loginMember.equals(LoginMember.NOT_LOGIN_MEMBER)) {
            return fare;
        }

        if (isTeenager(loginMember.getAge())) {
            return fare - getDiscountFare(TEENAGER_DISCOUNT_RATE);
        }

        if (isChild(loginMember.getAge())) {
            return fare - getDiscountFare(CHILD_DISCOUNT_RATE);
        }
        return fare;
    }

    private boolean isChild(int age) {
        return age >= CHILD_MIN_AGE && age < TEENAGER_MIN_AGE;
    }

    private boolean isTeenager(int age) {
        return age >= TEENAGER_MIN_AGE && age < TEENAGER_MAX_AGE;
    }

    private int getDiscountFare(double discountRate) {
        return (int) Math.round((fare - TAX_CREDIT) * discountRate);
    }

}
