package subway.util;

import subway.member.domain.LoginMember;

public class ChildFareStrategy implements FareStrategy{
    @Override
    public int getTotalFare(LoginMember loginMember, int totalFare) {
        totalFare -= (totalFare - DEDUCTION_FARE) * CHILD_SALE_RATE;
        return totalFare;
    }
}
