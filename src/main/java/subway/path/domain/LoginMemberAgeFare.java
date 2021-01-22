package subway.path.domain;

import subway.member.domain.LoginMember;

public class LoginMemberAgeFare {

    private static final int MIN_AGE_CHILD = 6;
    private static final int MIN_AGE_TEENAGER = 13;
    private static final int MIN_AGE_ADULT = 19;
    private static final int DEDUCTION_FARE = 350;
    private static final double CHILD_DISCOUNT_RATE = 0.5;
    private static final double TEENAGER_DISCOUNT_RATE = 0.2;

    public static int getFare(LoginMember loginMember, int fare) {
        int age = loginMember.getAge();
        if (age < MIN_AGE_CHILD) {
            return 0;
        }
        if (age < MIN_AGE_TEENAGER) {
            return fare - (int) ((fare - DEDUCTION_FARE) * CHILD_DISCOUNT_RATE);
        }
        if (age < MIN_AGE_ADULT) {
            return fare - (int) ((fare - DEDUCTION_FARE) * TEENAGER_DISCOUNT_RATE);
        }
        return fare;
    }
}
