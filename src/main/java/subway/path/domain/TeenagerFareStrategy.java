package subway.path.domain;

import subway.member.domain.LoginMember;

public class TeenagerFareStrategy implements FareStrategy{
    @Override
    public int getTotalFare(LoginMember loginMember, int totalFare) {
        totalFare -= (totalFare - DEDUCTION_FARE) * TEENAGER_SALE_RATE;
        return totalFare;
    }
}
