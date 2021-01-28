package subway.path.domain.farePolicy;

import subway.member.domain.LoginMember;

public class MemberPolicy extends ExtraFare {
    private static final int TEENAGER_MIN_AGE = 13;
    private static final int TEENAGER_MAX_AGE = 19;
    private static final int CHILD_MIN_AGE = 6;
    private static final int TAX_CREDIT = 350;
    private static final double TEENAGER_DISCOUNT_RATE = 0.2;
    private static final double CHILD_DISCOUNT_RATE = 0.5;
    public static final int NO_DISCOUNT_PAYMENT = 0;


    private LoginMember loginMember;
    private BasicFare payment;

    public MemberPolicy(BasicFare payment, LoginMember loginMember) {
        this.loginMember = loginMember;
        this.payment = payment;
    }

    @Override
    public int getFare() {
        return payment.getFare() - getExtraFare();
    }

    @Override
    public int getExtraFare() {
        if (loginMember.equals(LoginMember.NOT_LOGIN_MEMBER)) {
            return NO_DISCOUNT_PAYMENT;
        }

        if (loginMember.getAge() < CHILD_MIN_AGE) {
            return payment.getFare();
        }

        if (isChild(loginMember.getAge())) {
            return getDiscountFare(CHILD_DISCOUNT_RATE);
        }

        if (isTeenager(loginMember.getAge())) {
            return getDiscountFare(TEENAGER_DISCOUNT_RATE);
        }

        return NO_DISCOUNT_PAYMENT;
    }

    private boolean isChild(int age) {
        return age >= CHILD_MIN_AGE && age < TEENAGER_MIN_AGE;
    }

    private boolean isTeenager(int age) {
        return age >= TEENAGER_MIN_AGE && age < TEENAGER_MAX_AGE;
    }

    private int getDiscountFare(double discountRate) {
        return (int) Math.round((payment.getFare() - TAX_CREDIT) * discountRate);
    }

}
